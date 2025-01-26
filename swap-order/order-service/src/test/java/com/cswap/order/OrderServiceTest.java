package com.cswap.order;

import com.cswap.common.utils.MQUtil.RabbitMessageUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ContextConfiguration(classes = RedisConfig.class)
//@EnableFeignClients(basePackages = {"com.cswap.order.feignClient"})
class OrderServiceTest {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceTest.class);
    @Autowired
    RabbitMessageUtil rabbitMessageUtil;

    @Test
    void redisTest(){
        rabbitMessageUtil.sendMessage("123", "123", "111");
    }
/*    @Test
    void createOrder() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.submitOrder(1L);
        assert orderId != null;
        log.info(orderId.toString());
    }


    @Test
    void updateOrderPaymentType() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.submitOrder(1L);
        assert orderService.updateOrderPaymentType(orderId, DeliveryType.CASH_ON_DELIVERY);
        assert  orderService.getById(orderId).getDeliveryType() == DeliveryType.CASH_ON_DELIVERY;

        assert orderService.updateOrderPaymentType(orderId, DeliveryType.ONLINE_PAYMENT);
        assert  orderService.getById(orderId).getDeliveryType() == DeliveryType.ONLINE_PAYMENT;

    }

    @Test
    void updateOrderStatus() {
        productServiceClient.updateProductStatus(1L, ProductStatus.AVAILABLE);
        Long orderId = orderService.submitOrder(1L);
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
        Long orderId = orderService.submitOrder(1L);
        assert orderService.deleteOrder(orderId);
    }*/
}