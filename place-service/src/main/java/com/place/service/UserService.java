package com.place.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.place.common.domain.response.ResultResponse;
import com.place.domain.dto.LoginFormDTO;
import com.place.domain.dto.RegFormDTO;
import com.place.domain.po.PageBean;
import com.place.domain.po.User;
import com.place.domain.vo.UserLoginVO;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 条件分页查询
     *
     * @param page     页码
     * @param pageSize 每页展示记录数
     * @param name     姓名
     * @param phone    电话
     * @param begin    开始时间
     * @param end      结束时间
     * @return pageBean对象
     */
    PageBean page(Integer page, Integer pageSize, String name, String phone, LocalDate begin, LocalDate end);

    /**
     * 删除用户
     *
     * @param ids 待删除用户id
     */

    void delete(List<Integer> ids);

    /**
     * 查找用户名
     *
     * @param userName 用户名
     * @return 用户对象
     */
    User getUserByUserName(String userName);

    /**
     * 查找用户名对应角色
     *
     * @param userName 待删除用户名
     */
    String getUserRoleByUserName(String userName);

    /**
     * @param username 查找用户名
     * @return 是否存在
     */
    Boolean isExist(String username);

    void add(User user);


    void editProfile(User user);

    void editPass(String salt, String password, String username);

    UserLoginVO login(LoginFormDTO loginFormDTO);

    void register(RegFormDTO regFormDTO);
}
