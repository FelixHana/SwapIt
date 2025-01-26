package com.cswap.product.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZCY-
 */
@FeignClient(value = "order-api")
public interface OrderServiceClient {
    @GetMapping("/status/product")
    Boolean getValidOrder(@RequestParam("productId") Long productId);

}
