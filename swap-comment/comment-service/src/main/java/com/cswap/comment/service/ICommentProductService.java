package com.cswap.comment.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.comment.model.dto.CommentDto;
import com.cswap.comment.model.dto.EditCommentDto;
import com.cswap.comment.model.po.CommentProduct;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author ZCY-
 */
public interface ICommentProductService extends IService<CommentProduct> {

    CommentDto createComment(Long userId, EditCommentDto editCommentDto);

    Map<String, Object> queryProductComments(Long userId, Long productId) throws ExecutionException, InterruptedException;
}
