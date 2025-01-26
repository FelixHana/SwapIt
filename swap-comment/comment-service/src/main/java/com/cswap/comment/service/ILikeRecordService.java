package com.cswap.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.comment.model.dto.LikeDto;
import com.cswap.comment.model.po.Like;

public interface ILikeRecordService extends IService<Like> {

    void like(Long user, LikeDto likeDto);
}
