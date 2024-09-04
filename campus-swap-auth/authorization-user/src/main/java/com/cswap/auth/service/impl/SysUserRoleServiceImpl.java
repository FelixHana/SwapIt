package com.cswap.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.service.ISysUserRoleService;
import com.cswap.auth.domain.po.SysUserRole;
import com.cswap.auth.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色表 (多对多) 服务实现类
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

}
