package com.cswap.order.model.enums;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ZCY-
 */
@Getter
public enum OrderCodeEnum implements BaseCodeEnum {
    ORDER_NOT_EXIST(HttpStatus.OK, 20001, "订单不存在"),
    ORDER_EXIST(HttpStatus.OK, 20002, "订单已存在"),
    REVIEW_EXIST(HttpStatus.OK, 20002, "评价已存在"),
    REVIEW_NOT_EXIST(HttpStatus.OK, 20002, "评价不存在"),


    ORDER_CREATE_ERROR_PRODUCT(HttpStatus.OK, 20011, "订单创建失败-商品错误"),
    ORDER_CREATE_ERROR_LOCATION(HttpStatus.OK, 20012, "订单创建失败-地区错误"),
    ORDER_CREATE_ERROR_DB(HttpStatus.OK, 20013, "订单创建失败-数据库错误"),

    ORDER_DELETE_ERROR_PRODUCT(HttpStatus.OK, 20021, "订单删除失败-商品错误"),
    ORDER_DELETE_ERROR_STATUS(HttpStatus.OK, 20022, "订单删除失败-订单已发货"),
    ORDER_DELETE_ERROR_DB(HttpStatus.OK, 20023, "订单删除失败-数据库错误"),

    ORDER_UPDATE_ERROR_STATUS(HttpStatus.OK, 20032, "订单更新失败-不合法状态"),
    ORDER_UPDATE_ERROR_DB(HttpStatus.OK, 20033, "订单更新失败-数据库错误"),

    REVIEW_ERROR_USER(HttpStatus.OK, 21001, "评价-用户错误"),
    REVIEW_ERROR_STATUS(HttpStatus.OK, 21002, "评价-订单状态错误"),
    REVIEW_ERROR_DB(HttpStatus.OK, 21003, "评价-数据库错误"),

    ;
    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;
    OrderCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }
    @Override
    public String getEnumName() {
        return this.name();
    }
}
