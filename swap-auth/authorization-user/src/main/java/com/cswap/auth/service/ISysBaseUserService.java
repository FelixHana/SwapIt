package com.cswap.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.auth.domain.dto.UserRegDto;
import com.cswap.auth.domain.po.SysBaseUser;
import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import com.cswap.common.domain.dto.CommentUserDto;

import java.util.List;
import java.util.Map;


/**
 * @author ZCY-
 */
public interface ISysBaseUserService extends IService<SysBaseUser> {

    Long createUserByThirdAccount(SysOAuth2ThirdAccount account, String encodedPassword);

    void register(UserRegDto userRegDto);

    CommentUserDto queryCommentUserById(Long id);

    Map<Long, CommentUserDto> queryCommentUserListByIds(List<Long> userIdList);
}
