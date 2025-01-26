package com.cswap.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.order.model.dto.ReviewDto;
import com.cswap.order.model.po.Review;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 * @since 2024-08-19
 */
public interface IReviewService extends IService<Review> {

    Long createReview(ReviewDto reviewDto);

    Boolean deleteReview(Long reviewId);
}
