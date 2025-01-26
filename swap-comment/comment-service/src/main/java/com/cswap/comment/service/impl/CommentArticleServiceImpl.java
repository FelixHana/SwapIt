package com.cswap.comment.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.comment.mapper.CommentArticleMapper;
import com.cswap.comment.model.po.CommentArticle;
import com.cswap.comment.service.ICommentArticleService;
import org.springframework.stereotype.Service;

/**
 * @author ZCY-
 */
@Service
public class CommentArticleServiceImpl extends ServiceImpl<CommentArticleMapper, CommentArticle> implements ICommentArticleService {

}
