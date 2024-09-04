package com.cswap.order.feignClient;

import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.enums.ProductStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZCY-
 */
@FeignClient(value = "product-api")
public interface ProductServiceClient {
    @GetMapping
    ProductDto queryProductById(@RequestParam("productId") Long productId);

    @PutMapping("/status")
    Boolean updateProductStatus(@RequestParam("productId") Long productId, @RequestParam("productStatus") ProductStatus productStatus);
}
