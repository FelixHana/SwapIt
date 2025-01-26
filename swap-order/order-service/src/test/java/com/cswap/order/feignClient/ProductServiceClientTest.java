package com.cswap.order.feignClient;

import com.cswap.common.domain.dto.ProductDto;

import com.cswap.common.domain.enums.ProductStatus;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients(basePackages = {"com.cswap.order.feignClient"})
class ProductServiceClientTest {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClientTest.class);
    @Autowired
    ProductServiceClient productServiceClient;
/*    @Test
    @Order(1)
    void updateProductStatus() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Boolean result_1 = productServiceClient.updateProductStatus(1L, ProductStatus.SOLD);
        assert result_1;
        Boolean result_2 = productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        assert result_2;
    }*/
    @Test
    @Order(2)
    void queryProductById() {
        ProductDto result = productServiceClient.queryProductById(1L);
        assert result != null;
        log.info(result.getProductName());
    }


}