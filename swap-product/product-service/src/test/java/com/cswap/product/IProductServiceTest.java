package com.cswap.product;

import com.cswap.common.domain.dto.ProductImage;
import com.cswap.common.domain.enums.DeliveryType;
import com.cswap.common.utils.UserContext;
import com.cswap.product.model.dto.EditProductDto;
import com.cswap.product.service.IProductService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class IProductServiceTest {
    @Autowired
    IProductService productService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void redissonTest() {
        redisTemplate.opsForValue().set("product", "test");
    }

    @Test
    void updateProduct() {

    }

    @Test
    @Transactional
    void createProduct() {
        // Prepare a user
        UserContext.setUser(101L);
        assertNotNull(UserContext.getUser());

        EditProductDto editProductDto = new EditProductDto();
        ProductImage productImage = new ProductImage();
        productImage.setCount(2).setImages(List.of("https://pic1", "https://pic2"));

        editProductDto.setProductName("test prod name 123")
                .setProductDesc("I'm the fake desc...")
                .setCategory("1-1-1")
                .setPrice(BigDecimal.valueOf(123))
                .setImages(List.of("1", "2"))
                .setDelivery(DeliveryType.FREE_SHIPPING);
        Long result = productService.saveProduct(editProductDto);
        assertNotNull(result);
    }

    @Test
    void pageProduct() {
    }

    @Test
    @Transactional
    void updateProductStatus() {
        // Prepare a user
        UserContext.setUser(101L);
        assertNotNull(UserContext.getUser());
//        productService.updateProductStatus(1L, ProductStatus.AVAILABLE, newStatus);
//        assertTrue(productService.updateProductStatus(1L, ProductStatus.SOLD, newStatus));
//        assertTrue(productService.updateProductStatus(1L, ProductStatus.UNAVAILABLE, newStatus));

    }

    @Test
    void deleteProduct() {
    }
}