package com.cswap.comment.api;


import com.cswap.comment.model.dto.CommentDto;
import com.cswap.comment.model.dto.EditCommentDto;
import com.cswap.comment.service.ICommentProductService;
import com.cswap.common.utils.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author ZCY-
 */
@RestController
@RequestMapping("/comment-product")
@Tag(name = "商品评论接口")
@RequiredArgsConstructor
public class CommentProductController {
    private final ICommentProductService commentProductService;
    @Operation(summary = "测试接口方法")
    @GetMapping("/test")
    public String test() {
        return "received!";
    }

    @Operation(summary = "新增商品评论")
    @PostMapping
    public CommentDto saveComment(@RequestBody EditCommentDto editCommentDto) {
        return commentProductService.createComment(UserContext.getUser(), editCommentDto);
    }

    @Operation(summary = "查询商品评论")
    @GetMapping
    public Map<String, Object> queryProductComments(@RequestParam("productId") Long productId) throws ExecutionException, InterruptedException {
        return commentProductService.queryProductComments(UserContext.getUser(), productId);
    }
}
