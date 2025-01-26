package com.cswap.auth.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.auth.domain.dto.UserRegDto;
import com.cswap.auth.domain.po.*;
import com.cswap.auth.mapper.*;
import com.cswap.auth.service.ISysBaseUserService;
import com.cswap.auth.domain.enums.UserCodeEnum;
import com.cswap.auth.exception.UserException;
import com.cswap.common.constant.GlobalConstants;
import com.cswap.common.domain.dto.CommentUserDto;
import com.cswap.common.exception.CommonException;
import com.cswap.common.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过传入的账号信息查询对应的用户信息
        SysBaseUser user = lambdaQuery()
                //.eq(SysBaseUser::getAccountSource, "local")
                .eq(SysBaseUser::getAccount, username)

                .one();
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
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
    }

    @Override
    public Long createUserByThirdAccount(SysOAuth2ThirdAccount account, String encodedPassword) {
        SysBaseUser user = new SysBaseUser();
        user.setName(account.getName())
                .setAvatarUrl(account.getAvatarUrl())
                .setAccountSource(account.getPlatform())
                .setPassword(encodedPassword)
                .setAccount(account.getPlatform() + "_" + account.getUniqueId());
        boolean saved = save(user);
        if (!saved) {
            throw new UserException(UserCodeEnum.USER_CREATE_ERROR_DB, "系统用户创建失败");
        }
        setUserRole(user.getId(), GlobalConstants.ROLE_USER_ID);
        return user.getId();
    }

    @Override
    public void register(UserRegDto userRegDto) {
        // 用户名重复
        SysBaseUser existUser = lambdaQuery()
                .eq(SysBaseUser::getAccount, userRegDto.getAccount())
                .one();
        if (existUser != null) {
            throw new UserException(UserCodeEnum.USERNAME_EXIST, "用户名重复");
        }

        // 手机号重复
        existUser = lambdaQuery()
                .eq(SysBaseUser::getMobile, userRegDto.getMobile())
                .one();
        if (existUser != null) {
            throw new UserException(UserCodeEnum.MOBILE_EXIST, "手机号重复");
        }

        String encryptedPassword = passwordEncoder.encode(userRegDto.getPassword());
        // 添加用户
        SysBaseUser user = new SysBaseUser();
        user.setName(userRegDto.getName())
                .setAccount(userRegDto.getAccount())
                .setPassword(encryptedPassword)
                .setMobile(userRegDto.getMobile())
                .setAccountSource("local");
        boolean saved = save(user);
        if (!saved) {
            throw new UserException(UserCodeEnum.USER_CREATE_ERROR_DB, "系统用户创建失败");
        }
        setUserRole(user.getId(), GlobalConstants.ROLE_USER_ID);
    }

    @Override
    public CommentUserDto queryCommentUserById(Long id) {
        SysBaseUser user = lambdaQuery()
                .eq(SysBaseUser::getId, id)
                .one();
        if (user == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST);
        }
        CommentUserDto commentUserDto = new CommentUserDto();
        commentUserDto.setUsername(user.getName());
        commentUserDto.setAvatar(user.getAvatarUrl());
        return commentUserDto;
    }

    @Override
    public Map<Long, CommentUserDto> queryCommentUserListByIds(List<Long> userIdList) {
        return lambdaQuery()
                .in(SysBaseUser::getId, userIdList)
                .list()
                .stream()
                .collect(Collectors.toMap(SysBaseUser::getId, user -> {
                    CommentUserDto commentUserDto = new CommentUserDto();
                    commentUserDto.setUsername(user.getName());
                    commentUserDto.setAvatar(user.getAvatarUrl());
                    return commentUserDto;
                }));
    }

    private void setUserRole(Long userId, Long roleId){
        // 为用户设置角色
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        int inserted = sysUserRoleMapper.insert(userRole);
        if (inserted != 1) {
            throw new UserException(UserCodeEnum.USER_CREATE_ERROR_DB, "用户-角色表");
        }
    }
}
