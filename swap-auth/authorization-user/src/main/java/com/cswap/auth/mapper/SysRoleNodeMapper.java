package com.cswap.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.auth.domain.po.SysRoleNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色-功能关联表 Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Mapper
public interface SysRoleNodeMapper extends BaseMapper<SysRoleNode> {

}
