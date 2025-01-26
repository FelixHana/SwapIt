package com.cswap.order.listener;

import com.cswap.common.utils.MQUtil.MessageFailCause;
import com.cswap.common.utils.MQUtil.RabbitMessageUtil;
import com.cswap.common.utils.MQUtil.RedisMessageStore;
import com.cswap.order.model.enums.OrderCodeEnum;
import com.cswap.order.model.exception.OrderException;
import com.cswap.order.model.po.Order;
import com.cswap.order.service.IOrderService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;



@RabbitListener(queues = "order-release-order-queue")
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCancelListener {

    private final IOrderService orderService;

    private final RabbitMessageUtil rabbitMessageUtil;


    @RabbitHandler
    public void listener(String orderSn, Channel channel, Message message) throws IOException {
        log.info("MQ Consumer key=order.release.order 收到过期的订单信息 order={}", orderSn);
        try {
            orderService.closeOrder(orderSn);
//          throw new OrderException(OrderCodeEnum.ORDER_SERVICE_EXCEPTION);
            // 成功关单
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            rabbitMessageUtil.markMessageConsumed(message.getMessageProperties().getCorrelationId());
        } catch (Exception e) {
            // 处理失败重试及补偿逻辑
            rabbitMessageUtil.retryConsumeMessage(channel, message);
        }

    }


}
