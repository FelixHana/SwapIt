package com.cswap.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.domain.po.SysUserAddress;
import com.cswap.auth.mapper.SysUserAddressMapper;
import com.cswap.auth.service.ISysUserAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZCY-
 */
@Service
public class SysUserAddressServiceImpl extends ServiceImpl<SysUserAddressMapper, SysUserAddress> implements ISysUserAddressService {

    @Override
    public List<SysUserAddress> getUserAddressList(Long userId) {
        return lambdaQuery().eq(SysUserAddress::getUserId, userId).list();
    }
}
