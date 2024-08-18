package com.cswap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    /*
    //without page helper
    @Select("select * from project.user limit #{start}, #{pageSize}")
    public List<User> page(Integer start, Integer pageSize);

    @Select("select count(*) from project.user")
    public Long count();
    */

    // 分页查询用户列表
    List<User> page(String name, String phone, LocalDate begin, LocalDate end);

    // 批量删除用户
    void delete(List<Integer> ids);

    // 查找用户名对应用户
    User getUserByUserName(String userName);

    // 查找用户名对应角色
    Integer getUserRoleByUserName(String userName);

    // 查找用户名是否存在
    Boolean isExist(String userName);

    void add(User user);

    String getUsernameById(Long id);

    String getLocationByUsername(String username);

    User getUser(Long id);

    void editProfile(User user);

    void editPass(String salt, String password, String username);
}
