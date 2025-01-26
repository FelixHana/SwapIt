package com.cswap.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.product.mapper.CategoryBrandMapper;
import com.cswap.product.mapper.ProductCategoryMapper;
import com.cswap.product.model.dto.ProductCategoryTreeDto;

import com.cswap.product.model.exception.ProductException;
import com.cswap.product.model.po.CategoryBrand;
import com.cswap.product.model.po.ProductCategory;
import com.cswap.product.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cswap.product.model.enums.ProductCodeEnum.*;

/**
 * @author zcy
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory>
        implements IProductCategoryService {
    private final ProductCategoryMapper productCategoryMapper;

    private final CategoryBrandMapper categoryBrandMapper;

    private final RedissonClient redisson;

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    @Cacheable(value = "category", key = "'allCategories'")
    public List<ProductCategoryTreeDto> queryAllCategories() {
        return queryCategoryTreeFromDb(1L);
    }

/*    private List<ProductCategoryTreeDto> queryAllCategoryFromRedis() throws JsonProcessingException {
        // 缓存为JSON字符串
        String category = (String) redisTemplate.opsForValue().get("categoryJSON");
        ObjectMapper objectMapper = new ObjectMapper();
        if (StrUtil.isEmpty(category)) {
            // 从数据库查询并序列化
            List<ProductCategoryTreeDto> list = queryCategoryFromDb("1");
            String categoryJson = objectMapper.writeValueAsString(list);
            redisTemplate.opsForValue().set("categoryJSON", categoryJson);
            return list;
        }
        return objectMapper.readValue(category, new TypeReference<>() {
        });
    }*/

    private List<ProductCategoryTreeDto> queryCategoryTreeFromDb(Long categoryId) {
        // 查询所有节点 SQL预排序保证 orderBy 有序
        List<ProductCategoryTreeDto> tmpList = productCategoryMapper.selectTreeNodes(categoryId);
        // Map 记录每个节点的位置
        Map<Long, ProductCategoryTreeDto> productCategoryTreeDtoMap = tmpList.stream()
                .filter(item -> !categoryId.equals(item.getCategoryId()))
                .collect(Collectors.toMap(ProductCategory::getCategoryId, value -> value, (key1, key2) -> key2));
        // 结果列表存储一级节点
        List<ProductCategoryTreeDto> resultList = new ArrayList<>();
        // 构建树形结构
        tmpList.stream().filter(item -> !categoryId.equals(item.getCategoryId())).forEach(item -> {
            if (item.getParentId().equals(categoryId)) {
                resultList.add(item);
            }
            ProductCategoryTreeDto parentNode = productCategoryTreeDtoMap.get(item.getParentId());
            if (parentNode != null) {
                if (parentNode.getChildrenNodes() == null) {
                    parentNode.setChildrenNodes(new ArrayList<>());
                }
                parentNode.getChildrenNodes().add(item);
            }

        });
        return resultList;
    }

    @Override
    @Cacheable(value = "category", key = "'rootCategories'", sync = true)
    public List<ProductCategory> queryRootCategories() {
        return this.lambdaQuery()
                .eq(ProductCategory::getParentId, "1")
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "category", key = "'allCategories'"),
            @CacheEvict(value = "category", key = "'rootCategories'", condition = "#a0.parentId.equals(1L)")
    })
    public boolean updateCategory(ProductCategory productCategory) {
        ProductCategory old = this.getById(productCategory.getCategoryId());
        if (old == null) {
            return false;
        }
        boolean updated = this.updateById(productCategory);
        if (!updated) {
            throw new ProductException(CATEGORY_UPDATE_ERROR_DB);
        }
        // 更新 CategoryBrand 中的 category_name 冗余字段
        boolean updatedCB = categoryBrandMapper.updateByCategoryId(productCategory.getCategoryId(), productCategory.getCategoryName());
        if (!updatedCB) {
            throw new ProductException(CATEGORY_BRAND_UPDATE_ERROR_DB);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "category", key = "'allCategories'"),
            @CacheEvict(value = "category", key = "'rootCategories'", condition = "#a0.parentId.equals(1L)")
    })
    public boolean createCategoryCascade(ProductCategory productCategory) {
        if (productCategory.getParentId().equals(0L)) {
            return false;
        }
        if (productCategory.getParentId().equals(productCategory.getCategoryId())) {
            return false;
        }
        if (this.getById(productCategory.getCategoryId()) != null) {
            return false;
        }
        ProductCategory parent = this.lambdaQuery()
                .eq(ProductCategory::getCategoryId, productCategory.getParentId())
                .one();
        if (parent == null) {
            return false;
        }
        if (parent.getIsLeaf()) {
            this.lambdaUpdate()
                    .set(ProductCategory::getIsLeaf, false)
                    .eq(ProductCategory::getCategoryId, parent.getCategoryId())
                    .update();
        }
        productCategory.setIsLeaf(true);
        return this.save(productCategory);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "category", key = "'allCategories'"),
            @CacheEvict(value = "category", key = "'rootCategories'")
    })
    public boolean removeCategoryByIds(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        List<ProductCategory> categoryList = this.lambdaQuery().in(ProductCategory::getCategoryId, list).list();
        // 不能删除有子节点的项
        categoryList.forEach(item -> {
            if (!item.getIsLeaf()) {
                throw new ProductException(CATEGORY_CHILD_EXIST, item.getCategoryId().toString());
            }
        });

        // 判断品牌关联为空
        Integer count = categoryBrandMapper.selectCount(new LambdaQueryWrapper<CategoryBrand>()
                .in(CategoryBrand::getCategoryId, list));
        if (count != 0) {
            throw new ProductException(CATEGORY_RELATION_EXIST);
        }

        boolean removed = this.removeByIds(list);
        if (!removed) {
            throw new ProductException(CATEGORY_DELETE_ERROR_DB);
        }
        return true;
    }


}
