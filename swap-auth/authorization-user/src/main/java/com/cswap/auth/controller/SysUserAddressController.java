package com.cswap.auth.controller;


import com.cswap.auth.domain.po.SysUserAddress;
import com.cswap.auth.service.ISysUserAddressService;
import com.cswap.common.annotation.InternalApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ZCY-
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class SysUserAddressController {
    private final ISysUserAddressService sysUserAddressService;

    @InternalApi
    @GetMapping("/user/{userId}")
    public List<SysUserAddress> getUserAddressList(@PathVariable Long userId) {
        return sysUserAddressService.getUserAddressList(userId);
    }

    @InternalApi
    @GetMapping("/address/{id}")
    public SysUserAddress getAddress(@PathVariable Long id) {
        return sysUserAddressService.getById(id);
    }

    @PostMapping
    public Boolean saveAddress(@RequestBody SysUserAddress address) {
        return sysUserAddressService.save(address);
    }




}
