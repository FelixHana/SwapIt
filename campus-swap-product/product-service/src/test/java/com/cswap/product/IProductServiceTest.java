package com.cswap.product;

import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.dto.ProductImage;
import com.cswap.product.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class IProductServiceTest {
    @Autowired
    IProductService productService;

    @Test
    void updateProduct() {

    }

    @Test
    @Transactional
    void createProduct() {
        ProductDto productDTO = new ProductDto();
        ProductImage productImage = new ProductImage();
        productImage.setCount(2).setImages(List.of("https://pic1", "https://pic2"));

        productDTO.setProductName("test prod name 123")
                .setProductDesc("I'm the fake desc...")
                .setCategory("1-1-1")
                .setPrice(12345)
                .setImages(productImage)
                .setUniversityId(0L)
                .setIsCrossCampus(true);
        Long result = productService.createProduct(productDTO);

        assertNotNull(result);

    }

    @Test
    void pageProduct() {
    }

    @Test
    void updateProductStatus() {
    }

    @Test
    void deleteProduct() {
    }
}