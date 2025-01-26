package com.cswap.product.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RabbitMQConfig {

    private RabbitTemplate rabbitTemplate;

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }

    public void initRabbitTemplate() {
        //抵达 Broker 确认回调
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause) -> {
            log.info("ConfirmCallback correlationData[{}]==>ack:[{}]==>cause:[{}]", correlationData, ack, cause);
        });

        //没有投递给指定 queue 失败回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("ReturnsCallback Fail Message[{}]==>replyCode[{}]==>replyText[{}]==>exchange[{}]==>routingKey[{}]",
                    returnedMessage.getMessage(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getExchange(), returnedMessage.getRoutingKey());
        });
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
    public Exchange productEventExchange() {
        return new TopicExchange("product-event-exchange", true, false);
    }

    @Bean
    public Queue productReleaseProductQueue() {
        return new Queue("product-release-product-queue", true, false, false);
    }

    @Bean
    public Queue productDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "product-event-exchange");
        args.put("x-dead-letter-routing-key", "product.release");
        args.put("x-message-ttl", 10000);
        // TODO MQ ttl
        return new Queue("product-delay-queue", true, false, false, args);
    }

    @Bean
    public Binding productReleaseBinding() {
        return new Binding("product-release-product-queue",
                Binding.DestinationType.QUEUE,
                "product-event-exchange",
                "product.release.#", null);
    }

    @Bean
    public Binding productLockedBinding() {
        return new Binding("product-delay-queue",
                Binding.DestinationType.QUEUE,
                "product-event-exchange",
                "product.locked", null);
    }

}
