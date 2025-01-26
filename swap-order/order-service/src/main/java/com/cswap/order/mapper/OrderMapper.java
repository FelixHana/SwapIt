package com.cswap.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cswap.order.model.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author zcy
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Update("update orders set buyer_reviewed=#{isReviewed} where order_id=#{orderId}")
    int updateBuyerReviewed(@Param("orderId") Long orderId, @Param("isReviewed") boolean isReviewed);

    @Update("update orders set seller_reviewed=#{isReviewed} where order_id=#{orderId}")
    int updateSellerReviewed(@Param("orderId") Long orderId, @Param("isReviewed") boolean isReviewed);

}
