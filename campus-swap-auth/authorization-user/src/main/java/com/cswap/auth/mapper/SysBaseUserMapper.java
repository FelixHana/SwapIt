package com.cswap.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.auth.domain.po.SysBaseUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Mapper
public interface SysBaseUserMapper extends BaseMapper<SysBaseUser> {

}
