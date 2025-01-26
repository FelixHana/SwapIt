package com.cswap.common.utils.MQUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.cswap.common.constant.GlobalConstants.*;

@Component
@ConditionalOnBean(value = {RedisTemplate.class})
@RequiredArgsConstructor
public class RedisMessageStore {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     *  保存消息到 Redis
     */
    public void saveMessage(String messageId, String exchange, String routingKey, Object content) {
        // 路由键+id 作为 redis 中的键
        String messageKey = REDIS_MESSAGE_STORE_PREFIX + messageId;
        // 保存消息详情
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("exchange", exchange);
        messageData.put("routingKey", routingKey);
        messageData.put("content", content);
        messageData.put("timestamp", String.valueOf(Instant.now().toEpochMilli()));
        messageData.put("cause", null); // 0 - 发送端失败 / 1 - 消息投递失败 / 2 - 消费失败

        redisTemplate.opsForHash().putAll(messageKey, messageData);
        // 将消息加入待处理列表
        redisTemplate.opsForZSet().add(REDIS_MESSAGE_INDEX_PREFIX + "pending", messageId, Instant.now().toEpochMilli());
    }

    public void updateMessageConsumed(String messageId) {
        // 从 pending 列表中移除
        redisTemplate.opsForZSet().remove(REDIS_MESSAGE_INDEX_PREFIX + "pending", messageId);
    }

    public void updateMessageFailed(String messageId, MessageFailCause cause) {
        // 添加到 failed 列表中
        redisTemplate.opsForZSet().add(REDIS_MESSAGE_INDEX_PREFIX + "failed", messageId, Instant.now().toEpochMilli());
        redisTemplate.opsForZSet().remove(REDIS_MESSAGE_INDEX_PREFIX + "pending", messageId);
        // 设置失败原因
        redisTemplate.opsForHash().put(REDIS_MESSAGE_STORE_PREFIX + messageId, "cause", cause);
    }

    public void removeFailedMessage(String messageId) {
        // 失败消息处理后从 failed 列表移除
        redisTemplate.opsForZSet().remove(REDIS_MESSAGE_INDEX_PREFIX + "failed", messageId);
    }

    public void removePendingMessage(String messageId) {
        // 超时 pending 消息处理后从列表移除
        redisTemplate.opsForZSet().remove(REDIS_MESSAGE_INDEX_PREFIX + "pending", messageId);
    }

    /**
     *  消费消息手动 reject 重发不方便在消息头计数
     *  若消费出现异常则保存到 Redis 中
     */
    public Integer getConsumerRetryCount(String messageId) {
        Object count = redisTemplate.opsForHash().get(REDIS_MESSAGE_CONSUMER_RETRY + messageId, "count");
        if (count != null) {
            return (Integer) count;
        } else {
            return 0;
        }
    }

    public void updateConsumerRetryCount(String messageId, Integer retryCount) {
        redisTemplate.opsForHash().put(REDIS_MESSAGE_CONSUMER_RETRY + messageId, "count", retryCount);
    }





}
