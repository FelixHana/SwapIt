package com.cswap.product;

import com.cswap.product.mapper.ProductCategoryMapper;
import com.cswap.product.mapper.ProductMapper;
import com.cswap.product.model.dto.ProductDTO;
import com.cswap.product.model.po.ProductImage;
import com.cswap.product.service.IProductService;
import com.cswap.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        ProductDTO productDTO = new ProductDTO();
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