package com.cswap.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.utils.BeanUtils;
import com.cswap.order.mapper.OrderMapper;
import com.cswap.order.mapper.ReviewMapper;
import com.cswap.order.model.dto.ReviewDto;
import com.cswap.order.model.enums.OrderCodeEnum;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.enums.ReviewRole;
import com.cswap.order.model.exception.OrderException;
import com.cswap.order.model.po.Order;
import com.cswap.order.model.po.Review;
import com.cswap.order.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zcy
 * @since 2024-08-19
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements IReviewService {

    private final OrderMapper orderMapper;


    @Override
    public Long createReview(ReviewDto reviewDto) {
        // TODO get user id
        Long userId = 101L;
        Long orderId = reviewDto.getOrderId();
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new OrderException(OrderCodeEnum.ORDER_NOT_EXIST);
        }
        if(!order.getOrderStatus().equals(OrderStatus.COMPLETED)){
            throw new OrderException(OrderCodeEnum.REVIEW_ERROR_STATUS);
        }
        Review review = BeanUtils.copyBean(reviewDto, Review.class);
        review.setReviewerId(userId);
        if (reviewDto.getReviewRole().equals(ReviewRole.BUYER)) {
            if (!userId.equals(order.getBuyerId())) {
                throw new OrderException(OrderCodeEnum.REVIEW_ERROR_USER);
            }
            if (order.getBuyerReviewed().equals(true)) {
                throw new OrderException(OrderCodeEnum.REVIEW_EXIST);
            }
        } else if (reviewDto.getReviewRole().equals(ReviewRole.SELLER)) {
            if (!userId.equals(order.getSellerId())) {
                throw new OrderException(OrderCodeEnum.REVIEW_ERROR_USER);
            }
            if (order.getSellerReviewed().equals(true)) {
                throw new OrderException(OrderCodeEnum.REVIEW_EXIST);
            }
        }
        boolean saved = save(review);
        if (!saved) {
            throw new OrderException(OrderCodeEnum.REVIEW_ERROR_DB);
        }
        int updated;
        if (review.getReviewRole().equals(ReviewRole.SELLER)) {
            updated = orderMapper.updateSellerReviewed(orderId, true);
        } else {
            updated = orderMapper.updateBuyerReviewed(orderId, true);
        }
        if(updated < 1){
            throw new OrderException(OrderCodeEnum.ORDER_UPDATE_ERROR_DB);
        }
        return review.getReviewId();
    }

    @Override
    public Boolean deleteReview(Long reviewId) {
        // TODO get user id
        Long userId = 101L;
        Review review = getById(reviewId);
        if (review == null) {
            throw new OrderException(OrderCodeEnum.REVIEW_NOT_EXIST);
        }
        if (review.getReviewerId().equals(userId)) {
            throw new OrderException(OrderCodeEnum.REVIEW_ERROR_USER);
        }
        ReviewRole role = review.getReviewRole();
        // Set order.Buyer/SellerReviewed to false;
        int updated;
        if (role.equals(ReviewRole.SELLER)){
            updated = orderMapper.updateSellerReviewed(review.getOrderId(), false);
        } else {
            updated = orderMapper.updateBuyerReviewed(review.getOrderId(), false);
        }
        if(updated < 1){
            throw new OrderException(OrderCodeEnum.ORDER_UPDATE_ERROR_DB);
        }
        // Remove the review
        boolean removed = removeById(reviewId);
        if(!removed){
            throw new OrderException(OrderCodeEnum.REVIEW_ERROR_DB);
        }
        return true;
    }
}
