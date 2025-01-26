package com.cswap.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.auth.domain.po.SysUserAddress;

import java.util.List;

public interface ISysUserAddressService extends IService<SysUserAddress> {

    List<SysUserAddress> getUserAddressList(Long userId);
}
