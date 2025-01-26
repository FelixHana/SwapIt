package com.cswap.auth.controller;


import com.cswap.auth.service.ISysBaseUserService;
import com.cswap.common.annotation.InternalApi;
import com.cswap.common.domain.dto.CommentUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author ZCY-
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户信息接口")
public class SysBaseUserController {
    private final ISysBaseUserService sysBaseUserService;
    @InternalApi
    @Operation(summary = "评论用户信息")
    @GetMapping("/comment-user")
    public CommentUserDto queryCommentUserById(@RequestParam("userId") Long userId) {
        return sysBaseUserService.queryCommentUserById(userId);
    }

    @InternalApi
    @Operation(summary = "批量评论用户信息")
    @GetMapping("/comment-user/batch")
    public Map<Long, CommentUserDto> queryCommentUserListByIds(@Parameter(description = "用户id列表", example = "1, 2")
                                                                   @RequestParam("userIdList") List<Long> userIdList) {
        return sysBaseUserService.queryCommentUserListByIds(userIdList);
    }


}
