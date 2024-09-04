package com.cswap.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.common.domain.enums.PaymentType;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.po.Order;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zcy
 * @since 2024-08-18
 */
public interface IOrderService extends IService<Order> {

    Long createOrder(Long productId);

    Boolean deleteOrder(Long orderId);

    Boolean updateOrderPaymentType(Long orderId, PaymentType paymentType);

    Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
