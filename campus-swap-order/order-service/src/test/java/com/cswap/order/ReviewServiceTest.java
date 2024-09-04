package com.cswap.order;

import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.order.feignClient.ProductServiceClient;
import com.cswap.order.model.dto.ReviewDto;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.enums.ReviewRole;
import com.cswap.order.service.IOrderService;
import com.cswap.order.service.IReviewService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableFeignClients(basePackages = {"com.cswap.order.feignClient"})
class ReviewServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceTest.class);
    @Autowired
    IReviewService reviewService;

    @Autowired
    IOrderService orderService;

    @Autowired
    ProductServiceClient productServiceClient;
    @Test
    void createReview() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.createOrder(1L);
        orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED);
        ReviewDto reviewDto = new ReviewDto()
                .setOrderId(orderId)
                .setContent("review...")
                .setReviewRole(ReviewRole.BUYER)
                .setRating(5);
        Long reviewId = reviewService.createReview(reviewDto);
        assert reviewId != null;

    }

    @Test
    void deleteReview() {
    }
}