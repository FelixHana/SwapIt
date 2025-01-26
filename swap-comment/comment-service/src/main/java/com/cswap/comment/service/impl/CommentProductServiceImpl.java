package com.cswap.comment.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.comment.feignClient.UserServiceClient;
import com.cswap.comment.mapper.CommentProductMapper;
import com.cswap.comment.mapper.LikeRecordMapper;
import com.cswap.comment.model.dto.CommentDto;
import com.cswap.common.domain.dto.CommentUserDto;
import com.cswap.comment.model.dto.EditCommentDto;
import com.cswap.comment.model.enums.CommentCodeEnum;
import com.cswap.comment.model.exception.CommentException;
import com.cswap.comment.model.po.CommentProduct;
import com.cswap.comment.service.ICommentProductService;
import com.cswap.common.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.cswap.common.utils.ThreadPoolUtil.pool;

/**
 * @author ZCY-
 */
@Service
@RequiredArgsConstructor
public class CommentProductServiceImpl extends ServiceImpl<CommentProductMapper, CommentProduct> implements ICommentProductService {

    private final UserServiceClient userServiceClient;
    private final CommentProductMapper commentProductMapper;
    private final LikeRecordMapper likeRecordMapper;

    @Override
    public CommentDto createComment(Long userId, EditCommentDto editCommentDto) {
        // TODO comment count +1

        CommentProduct comment = BeanUtils.copyBean(editCommentDto, CommentProduct.class);
        // 设置商品id
        comment.setProductId(editCommentDto.getObjectId());
        comment.setUserId(userId);
        comment.setLikes(0);
        boolean saved = save(comment);
        if (!saved) {
            throw new CommentException(CommentCodeEnum.COMMENT_CREATE_ERROR_DB);
        }

        // TODO comment message

        // assemble comment DTO
        CommentUserDto commentUserDto = userServiceClient.queryCommentUserById(userId);
        return comment.convertToDto(commentUserDto);
    }

    @Override
    public Map<String, Object> queryProductComments(Long userId, Long productId) throws ExecutionException, InterruptedException {
        HashMap<String, Object> map = new HashMap<>(4);
        List<CommentProduct> rawCommentList = commentProductMapper.selectListByProductId(productId);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            List<CommentDto> commentDtoList;
            try {
                commentDtoList = queryCommentList(rawCommentList);
            } catch (Exception e) {
                throw new CommentException(CommentCodeEnum.COMMENT_QUERY_ERROR_DB);
            }
            map.put("commentList", commentDtoList);
        }, pool);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            List<Long> commentIdList = rawCommentList.stream().map(CommentProduct::getCommentId).collect(Collectors.toList());
            List<Long> likedCommentList = queryLikedCommentList(userId, commentIdList);
            map.put("likedCommentList", likedCommentList);
        }, pool);
        CompletableFuture<Void> tasks = CompletableFuture.allOf(future1, future2);
        tasks.get();
        return map;
    }



    private List<CommentDto> queryCommentList(List<CommentProduct> rawCommentList) {
        // 1. 抽取出 userId 列表
        List<Long> userIdList = rawCommentList.stream().map(CommentProduct::getUserId).collect(Collectors.toList());
        // 2. 查询用户信息 Map
        Map<Long, CommentUserDto> userMap = userServiceClient.queryCommentUserListByIds(userIdList);
        // 3. 组装评论内容和用户信息
        return buildCommentTree(rawCommentList, userMap);
        // TODO 评论点赞 缓存?
    }

    private List<CommentDto> buildCommentTree(List<CommentProduct> rawCommentList, Map<Long, CommentUserDto> userMap) {
        // 1. 构建id为键的Map用于定位父评论
        Map<Long, CommentDto> commentMap = rawCommentList.stream()
                // 转换为DTO 同时填充用户信息
                .map(comment -> comment.convertToDto(userMap.get(comment.getUserId())))
                .collect(Collectors.toMap(CommentDto::getId, comment -> comment));
        // 2. 构建树形结构
        List<CommentDto> rootCommentList = new ArrayList<>();
        for (CommentDto comment : commentMap.values()) {
            Long parentId = comment.getParentId();
            if (parentId == null) {
                // 一级评论
                rootCommentList.add(comment);
            } else {
                // 子评论 添加到父评论的回复列表中
                CommentDto parentComment = commentMap.get(parentId);
                if (parentComment != null) {
                    if (parentComment.getReply() == null){
                        parentComment.setReply(new HashMap<>(4));
                        parentComment.getReply().putIfAbsent("total", 0);
                        parentComment.getReply().putIfAbsent("list", new ArrayList<CommentDto>());
                    }
                    @SuppressWarnings("unchecked")
                    List<CommentDto> replyList = (List<CommentDto>) parentComment.getReply().get("list");
                    replyList.add(comment);
                    parentComment.getReply().put("total", replyList.size());
                }
            }
        }
        return rootCommentList;
    }

    private List<Long> queryLikedCommentList(Long userId, List<Long> commentIdList) {
        // TODO 数据库查询当前用户对商品下的留言点赞信息 用于反馈点赞状态
        return likeRecordMapper.selectListByUserIdAndProductId(userId, commentIdList);
    }
}
