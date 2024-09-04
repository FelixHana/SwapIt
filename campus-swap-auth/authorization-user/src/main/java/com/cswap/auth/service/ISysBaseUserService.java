package com.cswap.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.auth.domain.po.SysBaseUser;
import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
public interface ISysBaseUserService extends IService<SysBaseUser> {

    Long createUserByThirdAccount(SysOAuth2ThirdAccount account, String encodedPassword);
}
