package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.product.model.dto.ProductCategoryTreeDto;
import com.cswap.product.model.po.ProductCategory;

import java.util.List;


public interface IProductCategoryService extends IService<ProductCategory> {
    List<ProductCategoryTreeDto> queryAllCategories();
    List<ProductCategory> queryRootCategories();

    boolean updateCategory(ProductCategory productCategory);

    boolean createCategoryCascade(ProductCategory productCategory);

    boolean removeCategoryByIds(Long[] ids);
}
