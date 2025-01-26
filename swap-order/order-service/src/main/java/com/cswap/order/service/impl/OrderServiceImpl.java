package com.cswap.order.service.impl;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.mq.OrderTo;
import com.cswap.common.domain.enums.DeliveryType;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;
import com.cswap.common.utils.BeanUtils;
import com.cswap.common.utils.MQUtil.RabbitMessageUtil;
import com.cswap.common.utils.UserContext;
import com.cswap.order.feignClient.ProductServiceClient;
import com.cswap.order.feignClient.UserServiceClient;
import com.cswap.order.mapper.OrderMapper;
import com.cswap.order.model.dto.OrderConfirmDto;
import com.cswap.order.model.dto.OrderSubmitDto;
import com.cswap.order.model.enums.OrderCodeEnum;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.exception.OrderException;
import com.cswap.order.model.po.Order;
import com.cswap.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.cswap.common.constant.GlobalConstants.REDIS_ORDER_TOKEN_PREFIX;
import static com.cswap.common.utils.ThreadPoolUtil.pool;
import static com.cswap.order.model.enums.OrderCodeEnum.*;

/**
 * @author zcy
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient userServiceClient;

    private final RedisTemplate<String, Object> redisTemplate;

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMessageUtil rabbitMessageUtil;

    @Override
    public OrderConfirmDto confirmOrder(Long productId) throws ExecutionException, InterruptedException {
        Long userId = UserContext.getUser();

        OrderConfirmDto orderConfirmDto = new OrderConfirmDto();
        //当前线程请求头信息(user-info)
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 1. 获取用户地址列表
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            orderConfirmDto.setAddressList(userServiceClient.getUserAddressList(userId));
        }, pool);
        // 2. 获取商品信息
        CompletableFuture<Void> productFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            ProductDto productDto = productServiceClient.queryProductById(productId);
            orderConfirmDto.setProduct(productDto);
        }, pool);
        CompletableFuture.allOf(addressFuture, productFuture).get();
        if (orderConfirmDto.getProduct() == null) {
            throw new OrderException(ORDER_CREATE_ERROR_PRODUCT, "商品不存在");
        }
        if (orderConfirmDto.getProduct().getSellerId().equals(userId)) {
            throw new OrderException(ORDER_CREATE_ERROR_PRODUCT, "不能购买自己发布的商品");
        }
        // 防重令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(REDIS_ORDER_TOKEN_PREFIX + userId + ":" + productId, token, 30, TimeUnit.MINUTES);
        orderConfirmDto.setOrderToken(token);

        return orderConfirmDto;
    }

    /**
     * 商品服务 延时队列到期 被动解锁商品 检查订单状态
     * @param productId 下单商品 id
     * @return 存在有效的商品订单 - 后序无需解锁
     */
    @Override
    public Boolean getOrderStatusByProductId(Long productId) {
        Order order = this.lambdaQuery()
                .eq(Order::getProductId, productId)
                // 如果已经关单 不属于有效订单(关单后商品解锁没有成功) 商品应该解锁
                .ne(Order::getOrderStatus, OrderStatus.CANCELED)
                // 如果还未支付 同样不属于有效订单(订单过期 < 商品锁定过期 - 所以关单失败了) 商品应该解锁
                .ne(Order::getOrderStatus, OrderStatus.PENDING_PAYMENT)
                .one();
        return order != null;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String submitOrder(OrderSubmitDto submitDto) {
        Long userId = UserContext.getUser();
        // 验证订单令牌
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        String orderToken = submitDto.getOrderToken();

        // 通过 lua 脚本原子验证令牌
        Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                List.of(REDIS_ORDER_TOKEN_PREFIX + userId + ":" + submitDto.getProductId()),
                orderToken);
        if (result == null || result.equals(0L)) {
            throw new OrderException(ORDER_TOKEN_ERROR);
        }
        // 获取商品信息
        ProductDto productDto = productServiceClient.queryProductById(submitDto.getProductId());
        if (productDto == null) {
            throw new OrderException(ORDER_CREATE_ERROR_PRODUCT, "商品不存在 已售出");
        }
        // 创建订单对象
        Order order = createOrder(submitDto.getAddressId(), productDto);
        // 验价
        if (Math.abs(submitDto.getPayAmount().subtract(order.getPayAmount()).doubleValue()) >= 0.01) {
            throw new OrderException(ORDER_PAY_AMOUNT_ERROR);
        }
        // 保存订单
        boolean saved = save(order);
        if (!saved) {
            throw new OrderException(OrderCodeEnum.ORDER_CREATE_ERROR_DB);
        }
        // 更新商品状态
        Boolean updated = productServiceClient.orderLockProduct(submitDto.getProductId());
        if (!updated) {
            throw new OrderException(ORDER_CREATE_ERROR_PRODUCT, "商品状态锁定失败");
        }
        // 消息落库
        log.info("MQ Producer key = order.create.order 订单创建 商品锁定成功 order={}", order.getOrderSn());
        //rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",order.getOrderSn());
        rabbitMessageUtil.sendMessage("order-event-exchange", "order.create.order", order.getOrderSn());
        return order.getOrderSn();
    }

    private Order createOrder(Long addressId, ProductDto productDto) {
        Order order = new Order();
        BigDecimal total = productDto.getPrice();
        order.setOrderSn(IdWorker.getTimeId())
                .setProductId(productDto.getProductId())
                .setBuyerId(UserContext.getUser())
                .setOrderStatus(OrderStatus.PENDING_PAYMENT)
                .setSellerId(productDto.getSellerId())
                .setTotalAmount(total)
                .setDeliveryType(productDto.getDelivery())
                .setAddressId(addressId)
                .setAutoConfirmDays(7);
        // 处理邮费
        if (productDto.getDelivery().equals(DeliveryType.FIXED_SHIPPING)) {
            order.setPayAmount(total.add(productDto.getFreight()))
                    .setFreightAmount(productDto.getFreight());
        } else {
            order.setPayAmount(total).setFreightAmount(null);
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeOrder(String orderSn) {
        Order order = this.lambdaQuery()
                .eq(Order::getOrderSn, orderSn)
                .one();
        // 判断是否已经支付 未支付则关闭订单
        if (order.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT)) {
            this.lambdaUpdate()
                    .set(Order::getOrderStatus, OrderStatus.CANCELED)
                    .eq(Order::getOrderSn, orderSn)
                    .update();
            OrderTo orderTo = BeanUtils.copyBean(order, OrderTo.class);
            // 订单关单 主动向 product queue 发送消息 解锁商品
            //rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", orderTo);
            rabbitMessageUtil.sendMessage(
                    "order-event-exchange", "order.release.other", orderTo);
            log.info("MQ Producer key = order.release.other 未支付 主动解锁商品 {}", orderSn);
        }
    }

    @Override
    public Boolean deleteOrder(Long orderId) {
        Long userId = UserContext.getUser();
        Order order = getById(orderId);
        if (order == null || !userId.equals(order.getBuyerId())) {
            throw new OrderException(OrderCodeEnum.ORDER_NOT_EXIST, "订单不存在");
        }
        OrderStatus status = order.getOrderStatus();
        // Order can be cancelled before shipment
        if (status != OrderStatus.PENDING_PAYMENT && status != OrderStatus.PAID) {
            throw new OrderException(OrderCodeEnum.ORDER_DELETE_ERROR_STATUS);
        }

        Boolean result = productServiceClient.orderLockProduct(order.getProductId());
        if (!result) {
            throw new OrderException(OrderCodeEnum.ORDER_DELETE_ERROR_PRODUCT);
        }
        boolean removed = removeById(orderId);
        if (!removed) {
            throw new OrderException(OrderCodeEnum.ORDER_DELETE_ERROR_DB);
        }
        return true;
    }

    @Override
    public Boolean updateOrderPaymentType(Long orderId, DeliveryType deliveryType) {
        Long userId = UserContext.getUser();
        Order order = getById(orderId);
        if (order == null) {
            throw new OrderException(OrderCodeEnum.ORDER_NOT_EXIST);
        }
        if (!userId.equals(order.getBuyerId())) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "订单表买家id校验失败");
        }
        return lambdaUpdate()
                .eq(Order::getOrderId, orderId)
                .set(Order::getDeliveryType, deliveryType)
                .update();
    }

    @Override
    public Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Long userId = UserContext.getUser();
        Order order = getById(orderId);
        if (order == null) {
            throw new OrderException(OrderCodeEnum.ORDER_NOT_EXIST);
        }
        if (!userId.equals(order.getBuyerId())) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "订单表买家id校验失败");
        }
        if (order.getOrderStatus() == orderStatus) {
            return false;
        }
        if (!updateOrderTimes(orderId, orderStatus)) {
            return false;
        }
        return lambdaUpdate()
                .eq(Order::getOrderId, orderId)
                .set(Order::getOrderStatus, orderStatus)
                .update();

    }


    private boolean updateOrderTimes(Long orderId, OrderStatus orderStatus) {
        LocalDateTime currentTime = LocalDateTime.now();
        boolean updateResult;
        switch (orderStatus) {
            case PAID:
                updateResult = lambdaUpdate()
                        .eq(Order::getOrderId, orderId)
                        .set(Order::getPaymentTime, currentTime)
                        .update();
                break;
            case SHIPPED:
                updateResult = lambdaUpdate()
                        .eq(Order::getOrderId, orderId)
                        .set(Order::getShipmentTime, currentTime)
                        .update();
                break;
            case COMPLETED:
                updateResult = lambdaUpdate()
                        .eq(Order::getOrderId, orderId)
                        .set(Order::getCompleteTime, currentTime)
                        .update();
                break;
            case CANCELED:
                updateResult = lambdaUpdate()
                        .eq(Order::getOrderId, orderId)
                        .set(Order::getCloseTime, currentTime)
                        .update();
                break;
            default:
                throw new OrderException(OrderCodeEnum.ORDER_UPDATE_ERROR_STATUS);
        }
        return updateResult;
    }
}
