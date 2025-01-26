package com.cswap.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.utils.BeanUtils;
import com.cswap.product.mapper.BrandMapper;
import com.cswap.product.mapper.CategoryBrandMapper;
import com.cswap.product.mapper.ProductCategoryMapper;
import com.cswap.product.model.dto.CategoryBrandDto;
import com.cswap.product.model.exception.ProductException;
import com.cswap.product.model.po.Brand;
import com.cswap.product.model.po.CategoryBrand;
import com.cswap.product.model.po.ProductCategory;
import com.cswap.product.service.ICategoryBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cswap.product.model.enums.ProductCodeEnum.*;

@Service
@RequiredArgsConstructor
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements ICategoryBrandService {

    private final ProductCategoryMapper productCategoryMapper;
    private final BrandMapper brandMapper;

    @Override
    public boolean saveCategoryBrandRelation(CategoryBrandDto categoryBrandDto) {
        ProductCategory category = productCategoryMapper.selectById(categoryBrandDto.getCategoryId());
        Brand brand = brandMapper.selectById(categoryBrandDto.getBrandId());
        if (category == null) {
            throw new ProductException(CATEGORY_NOT_EXIST);
        }
        if (brand == null) {
            throw new ProductException(BRAND_NOT_EXIST);
        }
        // 冗余字段 name
        CategoryBrand categoryBrand = BeanUtils.copyBean(categoryBrandDto, CategoryBrand.class);
        categoryBrand.setBrandName(brand.getName());
        categoryBrand.setCategoryName(category.getCategoryName());
        boolean saved;
        try {
            saved = this.save(categoryBrand);
        } catch (DuplicateKeyException e) {
            throw new ProductException(CATEGORY_BRAND_EXIST);
        }
        if (!saved) {
            throw new ProductException(CATEGORY_BRAND_CREATE_ERROR_DB);
        }
        return true;
    }

    @Override
    public List<CategoryBrandDto> listByBrandId(Long brandId) {
        List<CategoryBrand> list = this.lambdaQuery().eq(CategoryBrand::getBrandId, brandId).list();
        return BeanUtils.copyList(list, CategoryBrandDto.class);
    }

    @Override
    public List<CategoryBrandDto> listByCategoryId(Long categoryId) {
        List<CategoryBrand> list = this.lambdaQuery().eq(CategoryBrand::getCategoryId, categoryId).list();
        return BeanUtils.copyList(list, CategoryBrandDto.class);
    }
}
