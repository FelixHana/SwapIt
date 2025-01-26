package com.cswap.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.comment.model.po.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeRecordMapper extends BaseMapper<Like> {

    List<Long> selectListByUserIdAndProductId(@Param("userId") Long userId, @Param("commentIdList") List<Long> commentIdList);

    Integer toggleLike(Like like);
}
