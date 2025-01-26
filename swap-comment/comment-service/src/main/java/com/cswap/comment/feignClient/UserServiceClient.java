package com.cswap.comment.feignClient;

import com.cswap.common.domain.dto.CommentUserDto;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.enums.ProductStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author ZCY-
 */
@FeignClient(value = "auth-api")
public interface UserServiceClient {
    @GetMapping("/user/comment-user")
    CommentUserDto queryCommentUserById(@RequestParam("userId") Long userId);

    @GetMapping("/user/comment-user/batch")
    Map<Long, CommentUserDto> queryCommentUserListByIds(@RequestParam("userIdList") List<Long> userIdList);
}
