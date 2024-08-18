package com.cswap.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.product.model.dto.ProductCategoryTreeDto;
import com.cswap.product.model.po.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author zcy
 */
@Mapper

public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    List<ProductCategoryTreeDto> selectTreeNodes(String categoryId);
}
