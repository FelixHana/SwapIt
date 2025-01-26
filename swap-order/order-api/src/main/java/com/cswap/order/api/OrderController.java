package com.cswap.order.api;


import com.cswap.common.annotation.InternalApi;
import com.cswap.common.domain.enums.DeliveryType;

import com.cswap.order.model.dto.OrderConfirmDto;
import com.cswap.order.model.dto.OrderSubmitDto;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

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

    @Operation(summary = "确认订单")
    @GetMapping("")
    public OrderConfirmDto confirmOrder(@RequestParam("productId") Long productId) throws ExecutionException, InterruptedException {
        return orderService.confirmOrder(productId);
    }

    @Operation(summary = "创建订单")
    @PostMapping("")
    public String submitOrder(@RequestBody OrderSubmitDto submitDto){
        return orderService.submitOrder(submitDto);
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("")
    public Boolean deleteOrder(@RequestParam("orderId") Long orderId){
        return orderService.deleteOrder(orderId);
    }

    @InternalApi
    @Operation(summary = "设置支付方式")
    @PutMapping("/payment")
    public Boolean updateOrderPaymentType(@RequestParam("orderId") Long orderId, @RequestParam("deliveryType") DeliveryType deliveryType){
        return orderService.updateOrderPaymentType(orderId, deliveryType);
    }

    @InternalApi
    @Operation(summary = "更新订单状态")
    @PutMapping("")
    public Boolean updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("orderStatus")OrderStatus orderStatus){
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @InternalApi
    @Operation(summary = "商品id查询有效订单")
    @GetMapping("/status/product")
    public Boolean getValidOrder(@RequestParam("productId") Long productId){
        return orderService.getOrderStatusByProductId(productId);
    }


}
