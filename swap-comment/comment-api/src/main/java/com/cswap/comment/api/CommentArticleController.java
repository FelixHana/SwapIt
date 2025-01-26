package com.cswap.comment.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZCY-
 */
@RestController
@RequestMapping("/comment-article")
@Tag(name = "文章评论接口")
public class CommentArticleController {

}
