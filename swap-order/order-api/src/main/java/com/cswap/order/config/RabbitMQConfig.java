package com.cswap.order.config;

import com.cswap.common.utils.MQUtil.MessageFailCause;
import com.cswap.common.utils.MQUtil.RabbitMessageUtil;
import com.cswap.common.utils.MQUtil.RedisMessageStore;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

   private final RedisMessageStore redisMessageStore;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitTemplateConfigurer configurer) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        configurer.configure(rabbitTemplate, connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        //确认回调
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause) -> {
            log.info("confirmCallback: correlationData[{}] ack:[{}] cause:[{}]", correlationData, ack, cause);
        });

        //失败回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            // 设置消息错误状态
            redisMessageStore.updateMessageFailed(returnedMessage.getMessage().getMessageProperties().getCorrelationId(), MessageFailCause.QUEUING_FAILED);
            log.info("ReturnsCallback: Fail Message[{}]==>replyCode[{}]==>replyText[{}]==>exchange[{}]==>routingKey[{}]",
                    returnedMessage.getMessage(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getExchange(), returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }


    @Bean
    public MessageConverter messageConverter() {
        // 解决发送 LocalDateTime
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Exchange orderExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order-release-order-queue", true, false, false);
    }

    @Bean
    public Queue orderDelayQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        arguments.put("x-message-ttl", 5000);
        return new Queue("order-delay-queue", true, false, false, arguments);
    }

    @Bean
    public Binding orderCreateBinding() {
        return new Binding("order-delay-queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);
    }

    @Bean
    public Binding orderReleaseBinding() {
        return new Binding("order-release-order-queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);
    }

    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("product-release-product-queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);
    }
}
