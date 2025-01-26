package com.cswap.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.common.domain.enums.DeliveryType;
import com.cswap.order.model.dto.OrderConfirmDto;
import com.cswap.order.model.dto.OrderSubmitDto;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.po.Order;

import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zcy
 * @since 2024-08-18
 */
public interface IOrderService extends IService<Order> {

    String submitOrder(OrderSubmitDto submitDto);

    Boolean deleteOrder(Long orderId);

    Boolean updateOrderPaymentType(Long orderId, DeliveryType deliveryType);

    Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus);

    OrderConfirmDto confirmOrder(Long productId) throws ExecutionException, InterruptedException;

    Boolean getOrderStatusByProductId(Long productId);

    void closeOrder(String orderSn);
}
