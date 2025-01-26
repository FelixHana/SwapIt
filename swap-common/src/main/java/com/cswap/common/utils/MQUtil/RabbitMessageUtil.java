package com.cswap.common.utils.MQUtil;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static com.cswap.common.constant.GlobalConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnBean(RabbitTemplate.class)
public class RabbitMessageUtil {
    private final RabbitTemplate rabbitTemplate;

    private final RedisMessageStore redisMessageStore;

    private final MessageConverter messageConverter;

    public void sendMessage(String exchange, String routingKey, Object content) {
        String correlationId = UUID.randomUUID().toString().replace("-", "");
        // 手动转换 Message (convertAndSend 也可以自动转换)
        Message message = messageConverter.toMessage(content, new MessageProperties());
        message.getMessageProperties().setCorrelationId(correlationId);
        redisMessageStore.saveMessage(correlationId, exchange, routingKey, content);

        int retry = 0;
        while(retry <= MQ_SEND_MAX_RETRY_COUNT){
            try {
                rabbitTemplate.convertAndSend(exchange, routingKey, message, new CorrelationData(correlationId));
                break;
            } catch (RuntimeException e) {
                if (retry == MQ_SEND_MAX_RETRY_COUNT) {
                    log.warn("MQ Producer send 重试次数过多 发送失败");
                    redisMessageStore.updateMessageFailed(correlationId, MessageFailCause.PRODUCER_FAILED);
                    break;
                } else {
                    retry++;
                    log.info("MQ Producer send 重试第 {} 次", retry);
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(MQ_SEND_RETRY_INTERVAL_MS));
                }
            }
        }
    }

    /**
     * 处理 RabbitListener 消费消息时抛异常的重试及补偿
     */
    public void retryConsumeMessage(Channel channel, Message message) throws IOException {
        String messageId = message.getMessageProperties().getCorrelationId();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Integer retry = redisMessageStore.getConsumerRetryCount(messageId);
        if (retry >= MQ_CONSUME_MAX_RETRY_COUNT) {
            log.warn("MQ Consumer receive 重试次数过多 消费失败");
            // 消息标记为 failed 不再 requeue
            redisMessageStore.updateMessageFailed(messageId, MessageFailCause.CONSUMER_FAILED);
            channel.basicReject(deliveryTag, false);
        } else {
            log.info("MQ Consumer receive 重试第 {} 次", retry + 1);
            redisMessageStore.updateConsumerRetryCount(message.getMessageProperties().getCorrelationId(), retry + 1);
            channel.basicReject(deliveryTag, true);
        }
    }

    /**
     * 在 redis 中标记消息已被成功消费
     * @param messageId 消息id
     */
    public void markMessageConsumed(String messageId) {
        redisMessageStore.updateMessageConsumed(messageId);
    }







}
