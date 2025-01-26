package com.cswap.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.product.model.po.ProductImages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductImagesMapper extends BaseMapper<ProductImages> {

    boolean saveImagesList(@Param("imageList") List<ProductImages> imageList);
}
