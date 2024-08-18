package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.product.model.dto.ProductCategoryTreeDto;
import com.cswap.product.model.po.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 * @since 2024-08-14
 */

public interface IProductCategoryService extends IService<ProductCategory> {
    List<ProductCategoryTreeDto> queryTreeNodes(String categoryId);
}
