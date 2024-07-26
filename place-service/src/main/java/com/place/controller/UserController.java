package com.place.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.place.common.domain.PageDTO;
import com.place.common.utils.BeanUtils;
import com.place.common.utils.UserContext;
import com.place.domain.dto.LoginFormDTO;
import com.place.domain.dto.RegFormDTO;
import com.place.domain.po.User;
import com.place.domain.query.UserQuery;
import com.place.domain.dto.UserDTO;
import com.place.domain.vo.UserLoginVO;
import com.place.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ZCY-
 */
@Api(tags = "用户管理相关接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    //条件分页查询
    @ApiOperation("查询用户接口")
    @GetMapping
    public PageDTO<UserDTO> page(UserQuery query) {
        Page<User> result = userService.lambdaQuery()
                .like(StrUtil.isNotBlank(query.getUsername()), User::getUsername, query.getUsername())
                .eq(StrUtil.isNotBlank(query.getPhone()), User::getPhone, query.getPhone())
                .between(query.getCreateTimeBegin() != null, User::getCreateTime, query.getCreateTimeBegin(), query.getCreateTimeEnd())
                .page(query.toMpPage("update_time", false));
        //log.info(UserContext.getUser().toString());
        return PageDTO.of(result, UserDTO.class);
    }


    //批量删除用户
    @ApiOperation("删除用户接口")
    @DeleteMapping("/{ids}")
    public List<Integer> delete(@PathVariable List<Integer> ids) {
        userService.removeByIds(ids);
        return ids;
    }
    // 登录
    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public UserLoginVO login(@Validated @RequestBody LoginFormDTO loginFormDTO) {
        return userService.login(loginFormDTO);

    }
    @ApiOperation("用户注册接口")
    @PostMapping("/reg")
    public String register(@Validated @RequestBody RegFormDTO regFormDTO) {
        userService.register(regFormDTO);
        return regFormDTO.getUsername();
    }

    @ApiOperation("用户信息接口")
    @GetMapping("/my")
    public UserDTO getUser() {
        User user = userService.lambdaQuery().eq(User::getId, UserContext.getUser()).one();
        return BeanUtils.copyBean(user, UserDTO.class);
    }

    @ApiOperation("编辑用户信息接口")
    @PostMapping("/editProfile")
    public UserDTO editProfile(@RequestBody UserDTO userDTO) {
        User user = BeanUtils.copyBean(userDTO, User.class);
        user.setId(UserContext.getUser());
        userService.updateById(user);
        return BeanUtils.copyBean(userService.lambdaQuery().eq(User::getUsername, userDTO.getUsername()).one(), UserDTO.class);
    }

//    @ApiOperation("修改密码接口")
//    @GetMapping("/editPass")
//    public String editPass(@RequestParam String oldPassword,
//                                   @RequestParam String password) {
//        Subject subject = SecurityUtils.getSubject();
//
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, oldPassword);
//        try {
//            subject.login(usernamePasswordToken);
//
//        } catch (IncorrectCredentialsException e) {
//            // 提示密码错误
//            return Result.error("password error");
//        }
//        List<String> result = SaltUtil.encryption(password);
//        userService.editPass(result.get(0), result.get(1), username);
//        String JwtToken = JwtUtil.createToken(username, userService.getUserRoleByUserName(username));
//        return JwtToken;
//    }
}
