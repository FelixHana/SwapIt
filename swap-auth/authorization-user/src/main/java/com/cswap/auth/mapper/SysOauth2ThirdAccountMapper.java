package com.cswap.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 第三方账号表 Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Mapper
public interface SysOauth2ThirdAccountMapper extends BaseMapper<SysOAuth2ThirdAccount> {

}
