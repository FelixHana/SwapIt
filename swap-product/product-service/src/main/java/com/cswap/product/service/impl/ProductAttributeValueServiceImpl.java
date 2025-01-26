package com.cswap.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.product.mapper.ProductAttributeValueMapper;
import com.cswap.product.model.po.ProductAttributeValue;
import com.cswap.product.service.IProductAttributeValueService;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeValueServiceImpl extends ServiceImpl<ProductAttributeValueMapper, ProductAttributeValue> implements IProductAttributeValueService {

}
