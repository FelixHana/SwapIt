package com.cswap.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.mq.OrderTo;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;
import com.cswap.common.utils.BeanUtils;
import com.cswap.common.utils.MQUtil.RabbitMessageUtil;
import com.cswap.common.utils.UserContext;
import com.cswap.product.feignClient.OrderServiceClient;
import com.cswap.product.mapper.AttributeMapper;
import com.cswap.product.mapper.BrandMapper;
import com.cswap.product.mapper.ProductMapper;
import com.cswap.product.model.dto.EditProductDto;
import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.product.model.exception.ProductException;
import com.cswap.product.model.po.Attribute;
import com.cswap.product.model.po.Brand;
import com.cswap.product.model.po.Product;
import com.cswap.product.model.po.ProductAttributeValue;
import com.cswap.product.model.query.ProductQuery;
import com.cswap.product.service.IProductAttributeValueService;
import com.cswap.product.service.IProductImagesService;
import com.cswap.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.cswap.product.model.enums.ProductCodeEnum.*;


/**
 * @author zcy
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    private final ProductMapper productMapper;

    private final BrandMapper brandMapper;

    private final AttributeMapper attributeMapper;

    private final IProductAttributeValueService productAttributeValueService;

    private final IProductImagesService productImagesService;

    private final OrderServiceClient orderServiceClient;

    private final RabbitMessageUtil rabbitMessageUtil;

    @Override
    public ProductDto updateProduct(EditProductDto editProductDTO) {
        Long userId = UserContext.getUser();
        log.info("user: {}", userId);
        // 校验已有商品信息
        Product product = productMapper.selectById(editProductDTO.getProductId());
        if (product == null) {
            throw new ProductException(PRODUCT_NOT_EXIST);
        }
        if (!userId.equals(product.getSellerId())) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "商品表用户id校验失败");
        }
        BeanUtils.copyProperties(editProductDTO, product);
        // 保存属性信息
        saveAttributes(editProductDTO, product.getProductId());
        // 更新商品信息
        if (productMapper.updateById(product) <= 0) {
            throw new ProductException(PRODUCT_UPDATE_ERROR_DB);
        }
        return getProduct(product.getProductId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveProduct(EditProductDto editProductDto) {
        Long userId = UserContext.getUser();
        log.info("user: {}", userId);
        Product product = BeanUtils.copyBean(editProductDto, Product.class);
        product.setSellerId(userId);
        product.setPublishTime(LocalDateTime.now());
        product.setProductStatus(ProductStatus.AVAILABLE);
        // 校验品牌信息
        Brand brand = brandMapper.selectById(product.getBrandId());
        if (brand == null) {
            throw new ProductException(BRAND_NOT_EXIST);
        }
        // 保存商品信息
        boolean saved = save(product);
        if (!saved) {
            throw new ProductException(PRODUCT_CREATE_ERROR_DB);
        }
        // 保存图片信息
        productImagesService.saveImages(editProductDto.getImages(), product.getProductId());
        // 保存属性信息
        saveAttributes(editProductDto, product.getProductId());

        return product.getProductId();
    }

    private void saveAttributes(EditProductDto editProductDto, Long productId) {
        List<ProductAttributeValue> attributeValues = editProductDto.getAttributes().stream().map(attr -> {
            ProductAttributeValue productAttributeValue = new ProductAttributeValue();
            BeanUtils.copyProperties(attr, productAttributeValue);
            productAttributeValue.setProductId(productId);
            return productAttributeValue;
        }).collect(Collectors.toList());
        List<Long> attributeIds = attributeValues.stream().map(ProductAttributeValue::getAttributeId).collect(Collectors.toList());
        Integer count = attributeMapper.selectCount(new LambdaQueryWrapper<Attribute>().in(Attribute::getId, attributeIds));
        if (count != attributeValues.size()) {
            throw new ProductException(ATTRIBUTE_NOT_EXIST);
        }
        boolean attrSaved = productAttributeValueService.saveBatch(attributeValues);
        if (!attrSaved) {
            throw new ProductException(ATTRIBUTE_VALUE_CREATE_ERROR_DB);
        }
    }

    @Override
    public Page<Product> pageProduct(ProductQuery query) {
        // TODO check user before querying products
        return lambdaQuery()
                .like(StrUtil.isNotBlank(query.getProductName()), Product::getProductName, query.getProductName())
                .eq(query.getCategory() != null, Product::getCategory, query.getCategory())
                .le(query.getPriceMax() != null, Product::getPrice, query.getPriceMax())
                .between(query.getCreateTimeBegin() != null, Product::getCreateTime, query.getCreateTimeBegin(), query.getCreateTimeEnd())
                .page(query.pageUpdateTimeDesc());
    }

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean orderLockProduct(Long productId) {
        Product product = getById(productId);
        if (product == null) {
            throw new ProductException(PRODUCT_NOT_EXIST);
        }
        if (product.getProductStatus() != ProductStatus.AVAILABLE) {
            return false;
        }
        boolean updated = lambdaUpdate()
                .set(Product::getProductStatus, ProductStatus.SOLD)
                .eq(productId != null, Product::getProductId, productId)
                .update();
        if (!updated) {
            throw new ProductException(PRODUCT_UPDATE_ERROR_DB);
        }
        log.info("MQ Producer key = product.locked 商品服务锁定成功 id={}", productId);
        //rabbitTemplate.convertAndSend("product-event-exchange","product.locked", product.getProductId());
        rabbitMessageUtil.sendMessage("product-event-exchange","product.locked", product.getProductId());
        return true;
    }


    @Override
    public void unlockProduct(Long productId) {
        Product product = this.lambdaQuery()
                .eq(Product::getProductId, productId)
                .one();
        // 商品标记为售出 检查订单是否回滚
        if (product != null && product.getProductStatus() == ProductStatus.SOLD) {
            Boolean valid = orderServiceClient.getValidOrder(productId);
            if (!valid) {
                // 订单不存在或已取消 解锁商品
                setProductAvailable(productId);
            }
        }
    }

    // 订单超时释放后主动解锁商品
    @Override
    public void unlockProduct(OrderTo order) {
        setProductAvailable(order.getProductId());
    }

    private void setProductAvailable(Long productId) {
        boolean updated = this.lambdaUpdate()
                .set(Product::getProductStatus, ProductStatus.AVAILABLE)
                .eq(Product::getProductId, productId)
                .update();
        if (!updated) {
            throw new ProductException(PRODUCT_UPDATE_ERROR_DB);
        }
    }


    @Override
    public Boolean deleteProduct(Long productId) {
        // TODO check user before deleting
        Product product = getById(productId);
        if (product == null) {
            throw new ProductException(PRODUCT_NOT_EXIST);
        }
        boolean removed = removeById(productId);
        if (!removed) {
            throw new ProductException(PRODUCT_DELETE_ERROR_DB);
        }
        return true;
    }

    @Override
    public ProductDto toggleProductStatus(Long productId) {
        Product product = getById(productId);
        if (product == null) {
            throw new ProductException(PRODUCT_NOT_EXIST);
        }
        ProductStatus currentStatus = product.getProductStatus();
        if (currentStatus == ProductStatus.SOLD) {
            throw new ProductException(PRODUCT_SOLD);
        }
        ProductStatus newStatus =
                (currentStatus == ProductStatus.AVAILABLE) ? ProductStatus.UNAVAILABLE : ProductStatus.AVAILABLE;
        boolean updated = lambdaUpdate()
                .set(Product::getProductStatus, newStatus)
                .eq(Product::getProductId, product.getProductId())
                .update();
        if (!updated) {
            throw new ProductException(PRODUCT_UPDATE_ERROR_DB);
        }
        return getProduct(productId);
    }

    @Override
    public ProductDto queryProduct(Long productId) {
        Product product = lambdaQuery()
                .eq(productId != null, Product::getProductId, productId)
                .eq(Product::getProductStatus, ProductStatus.AVAILABLE)
                .one();
        if (product != null) {
            return BeanUtils.copyBean(product, ProductDto.class);
        } else {
            return null;
        }
    }


    private ProductDto getProduct(Long productId) {
        // TODO check user before querying 1 product
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return null;
        }
        return BeanUtils.copyBean(product, ProductDto.class);
    }
}
