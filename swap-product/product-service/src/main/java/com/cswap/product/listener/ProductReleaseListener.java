package com.cswap.product.listener;

import com.cswap.common.domain.mq.OrderTo;
import com.cswap.common.utils.MQUtil.RabbitMessageUtil;
import com.cswap.product.service.IProductService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "product-release-product-queue")
@Service
public class ProductReleaseListener {
    private final IProductService productService;

    private final RabbitMessageUtil rabbitMessageUtil;

    @RabbitHandler
    public void handleProductRelease(Long productId, Message message, Channel channel) throws IOException {
        log.info("MQ Consumer key=product.release 延迟队列解锁商品 id={}", productId);
        try {
            productService.unlockProduct(productId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            rabbitMessageUtil.markMessageConsumed(message.getMessageProperties().getCorrelationId());
        } catch (Exception e) {
            rabbitMessageUtil.retryConsumeMessage(channel, message);
        }
    }

    // 格式不同的消息发同一个queue 通过消息携带的类选择 handler
    @RabbitHandler
    public void handleProductRelease(OrderTo order, Message message, Channel channel) throws IOException {
        log.info("MQ Consumer key=order.release.other 订单关闭后自动解锁商品 OrderSn={}", order.getOrderSn());
        try {
            productService.unlockProduct(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            rabbitMessageUtil.markMessageConsumed(message.getMessageProperties().getCorrelationId());
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            rabbitMessageUtil.retryConsumeMessage(channel, message);
        }
    }



}
