package com.cswap.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;
import com.cswap.common.utils.BeanUtils;
import com.cswap.product.mapper.ProductMapper;
import com.cswap.product.model.dto.EditProductDto;
import com.cswap.product.model.enums.ProductCodeEnum;
import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.product.model.exception.ProductException;
import com.cswap.product.model.po.Product;
import com.cswap.product.model.query.ProductQuery;
import com.cswap.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * @author zcy
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductDto updateProduct(EditProductDto editProductDTO) {
        // TODO get user id
        Long userId = 101L;
        Product product = productMapper.selectById(editProductDTO.getProductId());
        if (product == null) {
            throw new ProductException(ProductCodeEnum.PRODUCT_NOT_EXIST);
        }
        if (!userId.equals(product.getSellerId())) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "商品表用户id校验失败");
        }
        BeanUtils.copyProperties(editProductDTO, product);

        if (productMapper.updateById(product) <= 0) {
            throw new CommonException(CommonCodeEnum.DATABASE_UPDATE_ERROR);
        }
        return getProduct(product.getProductId());
    }

    @Override
    public Long createProduct(ProductDto productDTO) {
        // TODO get user id from security context
        Long tmpUserId = 101L;
        // Check location info...
        Product product = BeanUtils.copyBean(productDTO, Product.class);
        product.setSellerId(tmpUserId);
        product.setPublishTime(LocalDateTime.now());
        product.setProductStatus(ProductStatus.AVAILABLE);
        save(product);
        return product.getProductId();
    }

    @Override
    public Page<Product> pageProduct(ProductQuery query) {
        // TODO check user before querying products
        return lambdaQuery()
                .like(StrUtil.isNotBlank(query.getProductName()), Product::getProductName, query.getProductName())
                .eq(query.getCategory() != null, Product::getCategory, query.getCategory())
                .eq(query.getUniversityId() != null, Product::getUniversityId, query.getUniversityId())
                .le(query.getPriceMax() != null, Product::getPrice, query.getPriceMax())
                .between(query.getCreateTimeBegin() != null, Product::getCreateTime, query.getCreateTimeBegin(), query.getCreateTimeEnd())
                .page(query.toMpPage("update_time", false));
    }

    @Override
    public Boolean updateProductStatus(Long productId, ProductStatus productStatus) {
        // TODO check user before updating product
        Product product = getById(productId);
        if (product == null) {
            throw new ProductException(ProductCodeEnum.PRODUCT_NOT_EXIST);
        }
        if (product.getProductStatus() == productStatus){
            return false;
        }
        return lambdaUpdate()
                .set(productStatus != null, Product::getProductStatus, productStatus)
                .eq(productId != null, Product::getProductId, productId)
                .update();
    }


    @Override
    public Boolean deleteProduct(Long productId) {
        // TODO check user before deleting
        Product product = getById(productId);
        if (product == null) {
            throw new ProductException(ProductCodeEnum.PRODUCT_NOT_EXIST);
        }
        return removeById(productId);
    }

    @Override
    public ProductDto toggleProductStatus(Long productId) {
        Product product = getById(productId);
        if (product != null) {
            ProductStatus currentStatus = product.getProductStatus();
            if (currentStatus == ProductStatus.SOLD) {
                throw new ProductException(ProductCodeEnum.PRODUCT_SOLD);
            }
            ProductStatus newStatus =
                    (currentStatus == ProductStatus.AVAILABLE) ? ProductStatus.UNAVAILABLE : ProductStatus.AVAILABLE;
            lambdaUpdate()
                    .set(Product::getProductStatus, newStatus)
                    .eq(Product::getProductId, product.getProductId())
                    .update();
        } else {
            throw new ProductException(ProductCodeEnum.PRODUCT_NOT_EXIST);
        }
        return getProduct(productId);
    }

    @Override
    public ProductDto queryProduct(Long productId) {
        // TODO check user id
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
