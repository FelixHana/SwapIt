package com.cswap.comment.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.comment.model.po.CommentProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author ZCY-
 */
@Mapper
public interface CommentProductMapper extends BaseMapper<CommentProduct> {

    List<CommentProduct> selectListByProductId(Long productId);
}
