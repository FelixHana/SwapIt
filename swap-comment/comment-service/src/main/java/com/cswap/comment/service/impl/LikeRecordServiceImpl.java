package com.cswap.comment.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.comment.mapper.LikeRecordMapper;
import com.cswap.comment.model.dto.LikeDto;
import com.cswap.comment.model.enums.CommentCodeEnum;
import com.cswap.comment.model.exception.CommentException;
import com.cswap.comment.model.po.Like;
import com.cswap.comment.service.ILikeRecordService;
import com.cswap.common.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, Like> implements ILikeRecordService {
    private final LikeRecordMapper likeRecordMapper;

    @Override
    public void like(Long userId, LikeDto likeDto) {
        Like like = BeanUtils.copyBean(likeDto, Like.class);
        like.setUserId(userId);
        if (likeRecordMapper.toggleLike(like) <= 0){
            throw new CommentException(CommentCodeEnum.LIKE_ERROR_DB);
        }
        // TODO like message
    }
}
