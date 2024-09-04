package com.cswap.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.enums.PaymentType;
import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;
import com.cswap.order.feignClient.ProductServiceClient;
import com.cswap.order.mapper.OrderMapper;
import com.cswap.order.model.enums.OrderCodeEnum;
import com.cswap.order.model.enums.OrderStatus;
import com.cswap.order.model.exception.OrderException;
import com.cswap.order.model.po.Order;
import com.cswap.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zcy
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final ProductServiceClient productServiceClient;

    @Override
    public Long createOrder(Long productId) {
        // TODO get user id
        Long userId = 101L;
        // TODO get buyer's univ. id
        Long univId = 1L;
        ProductDto productDto = productServiceClient.queryProductById(productId);
        if (productDto == null) {
            throw new OrderException(OrderCodeEnum.ORDER_CREATE_ERROR_PRODUCT, "商品不存在");
        }
        if (productDto.getSellerId().equals(userId)){
            throw new OrderException(OrderCodeEnum.ORDER_CREATE_ERROR_PRODUCT, "不能购买自己发布的商品");
        }
        if (!productDto.getUniversityId().equals(univId) && !productDto.getIsCrossCampus()) {
            throw new OrderException(OrderCodeEnum.ORDER_CREATE_ERROR_LOCATION, "商品售卖区域不符");
        }
        Order order = new Order();
        order.setProductId(productId);
        order.setTotalPrice(productDto.getPrice());
        order.setBuyerId(userId);
        order.setSellerId(productDto.getSellerId());
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        // TODO Transaction ???
        Boolean result = productServiceClient.updateProductStatus(productId, ProductStatus.SOLD);
        if (!result) {
            throw new OrderException(OrderCodeEnum.ORDER_CREATE_ERROR_PRODUCT, "商品状态错误");
        }
        boolean saved = save(order);
        if (!saved) {
            throw new OrderException(OrderCodeEnum.ORDER_CREATE_ERROR_DB);
        }
        return order.getOrderId();
    }

    @Override
    public Boolean deleteOrder(Long orderId) {
        // TODO get user id
        Long userId = 101L;
        Order order = getById(orderId);
        if (order == null || !userId.equals(order.getBuyerId())) {
            throw new OrderException(OrderCodeEnum.ORDER_NOT_EXIST, "订单不存在");
        }
        OrderStatus status = order.getOrderStatus();
        // Order can be cancelled before shipment
        if (status != OrderStatus.PENDING_PAYMENT && status != OrderStatus.PAID) {
            throw new OrderException(OrderCodeEnum.ORDER_DELETE_ERROR_STATUS);
        }
        // Toggle related product status to available
        Boolean result = productServiceClient.updateProductStatus(order.getProductId(), ProductStatus.AVAILABLE);
        if (!result) {
            throw new OrderException(OrderCodeEnum.ORDER_DELETE_ERROR_PRODUCT);
        }
        boolean removed = removeById(orderId);
        if(!removed){
            throw new OrderException(OrderCodeEnum.ORDER_DELETE_ERROR_DB);
        }
        return true;
    }

    @Override
    public Boolean updateOrderPaymentType(Long orderId, PaymentType paymentType) {
        // TODO get user id
        Long userId = 101L;
        Order order = getById(orderId);
        if (order == null) {
            throw new OrderException(OrderCodeEnum.ORDER_NOT_EXIST);
        }
        if (!userId.equals(order.getBuyerId())) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "订单表买家id校验失败");
        }
        return lambdaUpdate()
                .eq(Order::getOrderId, orderId)
                .set(Order::getPaymentType, paymentType)
                .update();
    }

    @Override
    public Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        // TODO get user id
        Long userId = 101L;
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
        boolean updateResult = false;
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
