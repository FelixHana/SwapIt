package com.cswap.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.service.ISysRoleNodeService;
import com.cswap.auth.domain.po.SysRoleNode;
import com.cswap.auth.mapper.SysRoleNodeMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-功能关联表 服务实现类
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Service
public class SysRoleNodeServiceImpl extends ServiceImpl<SysRoleNodeMapper, SysRoleNode> implements ISysRoleNodeService {

}
