package com.cswap.product.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-attribute-value")
@Slf4j
@Tag(name = "商品属性接口")
public class ProductAttributeValueController {

}
