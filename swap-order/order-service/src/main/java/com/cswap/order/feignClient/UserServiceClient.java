package com.cswap.order.feignClient;

import com.cswap.order.model.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author ZCY-
 */
@FeignClient(value = "auth-api")
public interface UserServiceClient {
    @GetMapping("/address/user/{userId}")
    public List<AddressDto> getUserAddressList(@PathVariable("userId") Long userId);
}
