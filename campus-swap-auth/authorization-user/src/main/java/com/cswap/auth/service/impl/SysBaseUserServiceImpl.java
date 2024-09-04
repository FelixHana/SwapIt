package com.cswap.auth.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.domain.po.*;
import com.cswap.auth.mapper.*;
import com.cswap.auth.service.ISysBaseUserService;
import com.cswap.auth.domain.enums.UserCodeEnum;
import com.cswap.auth.exception.UserException;
import com.cswap.common.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zcy
 * @since 2024-08-27
 */
@Service
@RequiredArgsConstructor
public class SysBaseUserServiceImpl extends ServiceImpl<SysBaseUserMapper, SysBaseUser>
        implements ISysBaseUserService, UserDetailsService {
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleNodeMapper sysRoleNodeMapper;
    private final SysNodeMapper sysNodeMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 在Security中“username”代表了用户登录时输入的账号，在重写该方法时它可以代表以下内容：账号、手机号、邮箱、姓名等
        // username 在数据库中不一定非要是一样的列，可以是手机号、邮箱，也可以都是，最主要的目的就是根据输入的内容获取到对应的用户信息
        // 通过传入的账号信息查询对应的用户信息
        SysBaseUser user = lambdaQuery()
                //.eq(SysBaseUser::getAccountSource, "local")
                .eq(SysBaseUser::getAccount, username)

                .one();
        if (user == null) {
            throw new UserException(UserCodeEnum.USERNAME_NOT_EXIST);
        }
        // 用户-角色表查询对应的角色
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, user.getId()));
        List<Long> rolesId = Optional.ofNullable(userRoles).orElse(Collections.emptyList()).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        if (BeanUtils.isEmpty(rolesId)) {
            return user;
        }
        List<SysRole> roles = sysRoleMapper.selectBatchIds(rolesId);
        List<String> roleNames = Optional.ofNullable(roles).orElse(Collections.emptyList()).stream().map(SysRole::getRoleName).collect(Collectors.toList());
        Set<SimpleGrantedAuthority> authorities = Optional.ofNullable(roles).orElse(Collections.emptyList()).stream().map(SysRole::getRoleName).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        user.setAuthorities(authorities);
        user.setUserRoleNames(roleNames);
        return user;
//        // 角色-菜单表查出对应菜单
//        List<SysRoleNode> roleMenus = sysRoleNodeMapper.selectList(Wrappers.lambdaQuery(SysRoleNode.class).in(SysRoleNode::getRoleId, rolesId));
//        List<Long> menusId = Optional.ofNullable(roleMenus).orElse(Collections.emptyList()).stream().map(SysRoleNode::getNodeId).collect(Collectors.toList());
//        if (BeanUtils.isEmpty(menusId)) {
//            return user;
//        }
//        // 根据菜单ID查出菜单，放入user的authorities
//        List<SysNode> menus = sysNodeMapper.selectBatchIds(menusId);
//        Set<SimpleGrantedAuthority> authorities = Optional.ofNullable(menus).orElse(Collections.emptyList()).stream().map(SysNode::getUrl).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
//        user.setAuthorities(authorities);
//        return user;
    }

    @Override
    public Long createUserByThirdAccount(SysOAuth2ThirdAccount account, String encodedPassword) {
        SysBaseUser user = new SysBaseUser();
        user.setName(account.getName())
                .setAvatarUrl(account.getAvatarUrl())
                .setAccountSource(account.getPlatform())
                .setPassword(encodedPassword)
                .setAccount(account.getPlatform() + "_" +account.getUniqueId());
        boolean saved = save(user);
        if(!saved){
            throw new UserException(UserCodeEnum.USER_CREATE_ERROR_DB, "系统用户");
        }
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getId());
        // TODO create role id or name constants
        userRole.setRoleId(5L);
        int inserted = sysUserRoleMapper.insert(userRole);
        if(inserted != 1){
            throw new UserException(UserCodeEnum.USER_CREATE_ERROR_DB, "用户-角色表");
        }
        return user.getId();
    }
}
