package com.cswap.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.product.model.po.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotEmpty;

@Mapper
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {

    boolean updateByBrandId(@Param("brandId") Long brandId,
                            @Param("brandName") @NotEmpty String brandName);

    boolean updateByCategoryId(@Param("categoryId") Long categoryId,
                               @Param("categoryName") @NotEmpty String categoryName);
}
