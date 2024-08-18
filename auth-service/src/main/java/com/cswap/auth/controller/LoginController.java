package com.cswap.auth.controller;


import com.cswap.ucenter.mapper.XcUserMapper;
import com.cswap.ucenter.model.po.XcUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZCY-
 */
@Slf4j
@RestController
public class LoginController {

    final XcUserMapper userMapper;

    public LoginController(XcUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping("/login-success")
    public String loginSuccess(){

        return "登录成功";
    }


    @RequestMapping("/user/{id}")
    public XcUser getuser(@PathVariable("id") String id){
        return userMapper.selectById(id);
    }

    @RequestMapping("/r/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1(){
        return "访问r1资源";
    }

    @RequestMapping("/r/r2")
    @PreAuthorize("hasAuthority('p2')")
    public String r2(){
        return "访问r2资源";
    }



}