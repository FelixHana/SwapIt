package com.cswap.order.api;

import com.cswap.order.model.dto.ReviewDto;
import com.cswap.order.service.IReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZCY-
 */
@RestController
@Tag(name = "订单评价接口")
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final IReviewService reviewService;
    @Operation(summary = "测试接口方法")
    @GetMapping("/test")
    public String test() {
        return "received!";
    }

    @Operation(summary = "新增评论")
    @PostMapping
    public Long saveReview(@RequestBody ReviewDto reviewDto){
        return reviewService.createReview(reviewDto);
    }

    @Operation(summary = "删除评论")
    @DeleteMapping
    public Boolean deleteReview(@RequestParam("reviewId") Long reviewId){
        return reviewService.deleteReview(reviewId);
    }

}
