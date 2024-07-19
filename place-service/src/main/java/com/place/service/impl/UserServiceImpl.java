package com.place.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.place.common.domain.enums.UserCodeEnum;
import com.place.common.domain.response.ResultResponse;
import com.place.common.exception.CommonException;
import com.place.common.exception.UserException;
import com.place.domain.dto.LoginFormDTO;
import com.place.domain.dto.RegFormDTO;
import com.place.domain.po.PageBean;
import com.place.domain.po.User;
import com.place.domain.vo.UserLoginVO;
import com.place.service.UserService;
import com.place.mapper.UserMapper;
import com.place.utils.JwtUtil;
import com.place.utils.salt.SaltUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    //without page helper
    /*@Override
    public PageBean page(Integer page, Integer pageSize) {
        Long count = userMapper.count();
        Integer start = (page - 1) * pageSize;
        List<User> userList = userMapper.page(start, pageSize);
        return new PageBean(count, userList);
    }*/

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String phone, LocalDate begin, LocalDate end) {
        // 设置分页参数
        PageHelper.startPage(page, pageSize);
        // 执行分页查询
        List<User> userList = userMapper.page(name, phone, begin, end);
        // 获取分页结果
        Page<User> p = (Page<User>) userList;
        //封装PageBean
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        userMapper.delete(ids);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public String getUserRoleByUserName(String userName) {
        return userMapper.getUserRoleByUserName(userName).toString();

    }

    @Override
    public Boolean isExist(String username) {
        int count = lambdaQuery().eq(User::getUsername, username).count();
        //return userMapper.isExist(username);
        return count > 0;
    }

    //普通用户注册
    @Override
    public void add(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setUserRole(0);
        userMapper.add(user);
    }



    @Override
    public void editProfile(User user) {
        userMapper.editProfile(user);
    }

    @Override
    public void editPass(String salt, String password, String username) {
        userMapper.editPass(salt, password, username);
    }

    @Override
    public UserLoginVO login(LoginFormDTO loginFormDTO) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginFormDTO.getUsername(), loginFormDTO.getPassword());
        try {
            subject.login(token);
            User user = lambdaQuery().eq(User::getUsername, loginFormDTO.getUsername()).one();
            String jwt = JwtUtil.createJwt(user.getId());
            // 返回给前端
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setUserId(userLoginVO.getUserId());
            userLoginVO.setUsername(user.getUsername());
            userLoginVO.setToken(jwt);
            userLoginVO.setRole(user.getUserRole());

            return userLoginVO;
        } catch (UnknownAccountException e) {
            // 账户错误
            throw new UserException(UserCodeEnum.AUTH_ERROR);
        } catch (IncorrectCredentialsException e) {
            // 密码错误
            throw new UserException(UserCodeEnum.AUTH_ERROR);
        }
    }

    @Override
    public void register(RegFormDTO regFormDTO) {
        if (isExist(regFormDTO.getUsername())) {
            throw new UserException(UserCodeEnum.USERNAME_EXIST);
        }
        List<String> result = SaltUtil.encryption(regFormDTO.getPassword());
        User newUser = new User();
        newUser.setUsername(HtmlUtils.htmlEscape(regFormDTO.getUsername()));
        newUser.setUserSalt(result.get(0));
        newUser.setUserPassword(result.get(1));
        newUser.setName(regFormDTO.getName());
        newUser.setLocation(Integer.valueOf(regFormDTO.getLocation()));
        add(newUser);
    }
}
