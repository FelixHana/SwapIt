package com.cswap.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.product.model.po.ProductAttributeValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductAttributeValueMapper extends BaseMapper<ProductAttributeValue> {

//    boolean saveAttributes(@Param("attributeValues") List<ProductAttributeValue> attributeValues);
}
