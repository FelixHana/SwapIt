package com.cswap.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;

/**
 * <p>
 * 第三方账号表 服务类
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
public interface ISysOAuth2ThirdAccountService extends IService<SysOAuth2ThirdAccount> {

    String createThirdUser(SysOAuth2ThirdAccount oAuth2ThirdAccount);
}
