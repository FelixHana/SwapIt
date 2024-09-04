package com.cswap.order;

import com.cswap.common.domain.enums.PaymentType;
import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.order.feignClient.ProductServiceClient;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.service.IOrderService;
import com.cswap.order.service.IReviewService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients(basePackages = {"com.cswap.order.feignClient"})
class OrderServiceTest {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceTest.class);
    @Autowired
    IOrderService orderService;

    @Autowired
    ProductServiceClient productServiceClient;
    @Test
    void createOrder() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.createOrder(1L);
        assert orderId != null;
        log.info(orderId.toString());
    }


    @Test
    void updateOrderPaymentType() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.createOrder(1L);
        assert orderService.updateOrderPaymentType(orderId, PaymentType.CASH_ON_DELIVERY);
        assert  orderService.getById(orderId).getPaymentType() == PaymentType.CASH_ON_DELIVERY;

        assert orderService.updateOrderPaymentType(orderId, PaymentType.ONLINE_PAYMENT);
        assert  orderService.getById(orderId).getPaymentType() == PaymentType.ONLINE_PAYMENT;

    }

    @Test
    void updateOrderStatus() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.createOrder(1L);
        assert orderService.updateOrderStatus(orderId, OrderStatus.PAID);
        assert orderService.getById(orderId).getOrderStatus() == OrderStatus.PAID;

        assert orderService.updateOrderStatus(orderId, OrderStatus.SHIPPED);
        assert orderService.getById(orderId).getOrderStatus() == OrderStatus.SHIPPED;

        assert orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED);
        assert orderService.getById(orderId).getOrderStatus() == OrderStatus.COMPLETED;

        assert orderService.updateOrderStatus(orderId, OrderStatus.CANCELED);
        assert orderService.getById(orderId).getOrderStatus() == OrderStatus.CANCELED;
    }
    @Test
    void deleteOrder() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.createOrder(1L);
        assert orderService.deleteOrder(orderId);
    }
}