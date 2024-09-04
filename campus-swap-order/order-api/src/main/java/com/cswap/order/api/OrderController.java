package com.cswap.order.api;


import com.cswap.common.annotation.InternalApi;
import com.cswap.common.domain.enums.PaymentType;

import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zcy
 */
@RestController
@Tag(name = "订单接口")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    @Operation(summary = "测试接口方法")
    @GetMapping("/test")
    public String test() {
        return "received!";
    }

    @Operation(summary = "新增订单")
    @PostMapping
    public Long saveOrder(@RequestParam("productId") Long productId){
        return orderService.createOrder(productId);
    }

    @Operation(summary = "删除订单")
    @DeleteMapping
    public Boolean deleteOrder(@RequestParam("orderId") Long orderId){
        return orderService.deleteOrder(orderId);
    }

    @InternalApi
    @Operation(summary = "设置支付方式")
    @PutMapping("/payment")
    public Boolean updateOrderPaymentType(@RequestParam("orderId") Long orderId, @RequestParam("paymentType") PaymentType paymentType){
        return orderService.updateOrderPaymentType(orderId, paymentType);
    }

    @InternalApi
    @Operation(summary = "更新订单状态")
    @PutMapping
    public Boolean updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("orderStatus")OrderStatus orderStatus){
        return orderService.updateOrderStatus(orderId, orderStatus);
    }


}
