package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.product.model.dto.CategoryBrandDto;
import com.cswap.product.model.po.CategoryBrand;

import java.util.List;

public interface ICategoryBrandService extends IService<CategoryBrand> {

    boolean saveCategoryBrandRelation(CategoryBrandDto categoryBrandDto);

    List<CategoryBrandDto> listByBrandId(Long brandId);

    List<CategoryBrandDto> listByCategoryId(Long categoryId);
}
