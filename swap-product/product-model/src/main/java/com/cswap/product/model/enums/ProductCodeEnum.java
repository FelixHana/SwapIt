package com.cswap.product.model.enums;

import com.cswap.common.domain.enums.exceptions.BaseCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ZCY-
 */

@Getter
public enum ProductCodeEnum implements BaseCodeEnum {
    /**
     * 商品错误码
     */
    PRODUCT_EXIST(HttpStatus.OK, 10001, "商品已存在"),
    PRODUCT_NOT_EXIST(HttpStatus.OK, 10002, "商品不存在"),
    PRODUCT_SOLD(HttpStatus.OK, 10003, "商品已售出"),
    PRODUCT_NOT_AVAILABLE(HttpStatus.OK, 10004, "商品不可售"),
    PRODUCT_SELLER_NOT_MATCH(HttpStatus.OK, 10005, "商品卖家不匹配"),
    PRODUCT_IMAGE_SAVE_ERROR(HttpStatus.OK, 10006, "商品图保存出错"),


    PRODUCT_CREATE_ERROR_DB(HttpStatus.OK, 10011, "商品创建失败-数据库错误"),


    PRODUCT_DELETE_ERROR_DB(HttpStatus.OK, 10021, "商品删除失败-数据库错误"),


    PRODUCT_UPDATE_ERROR_DB(HttpStatus.OK, 10031, "商品更新失败-数据库错误"),

    /**
     * 分类错误码
     */
    CATEGORY_EXIST(HttpStatus.OK, 11001, "分类已存在"),
    CATEGORY_NOT_EXIST(HttpStatus.OK, 11002, "分类不存在"),
    CATEGORY_RELATION_EXIST(HttpStatus.OK, 11003, "分类有品牌关联"),
    CATEGORY_CHILD_EXIST(HttpStatus.OK, 11003, "分类有子节点"),


    CATEGORY_CREATE_ERROR_DB(HttpStatus.OK, 11011, "分类创建失败-数据库错误"),


    CATEGORY_DELETE_ERROR_DB(HttpStatus.OK, 11021, "分类删除失败-数据库错误"),


    CATEGORY_UPDATE_ERROR_DB(HttpStatus.OK, 11031, "分类更新失败-数据库错误"),

    /**
     * 品牌错误码
     */
    BRAND_EXIST(HttpStatus.OK, 12001, "品牌已存在"),
    BRAND_NOT_EXIST(HttpStatus.OK, 12002, "品牌不存在"),

    BRAND_CREATE_ERROR_DB(HttpStatus.OK, 12011, "品牌创建失败-数据库错误"),


    BRAND_DELETE_ERROR_DB(HttpStatus.OK, 12021, "品牌删除失败-数据库错误"),


    BRAND_UPDATE_ERROR_DB(HttpStatus.OK, 12031, "品牌更新失败-数据库错误"),

    /**
     * 分类-品牌关联错误码
     */
    CATEGORY_BRAND_EXIST(HttpStatus.OK, 13001, "分类-品牌关联已存在"),
    CATEGORY_BRAND_NOT_EXIST(HttpStatus.OK, 13002, "分类-品牌关联不存在"),

    CATEGORY_BRAND_CREATE_ERROR_DB(HttpStatus.OK, 13011, "分类-品牌关联创建失败-数据库错误"),


    CATEGORY_BRAND_DELETE_ERROR_DB(HttpStatus.OK, 13021, "分类-品牌关联删除失败-数据库错误"),


    CATEGORY_BRAND_UPDATE_ERROR_DB(HttpStatus.OK, 13031, "分类-品牌关联更新失败-数据库错误"),
    /**
     * 属性错误码
     */
    ATTRIBUTE_EXIST(HttpStatus.OK, 14001, "属性已存在"),
    ATTRIBUTE_NOT_EXIST(HttpStatus.OK, 14002, "属性不存在"),

    ATTRIBUTE_CREATE_ERROR_DB(HttpStatus.OK, 14011, "属性创建失败-数据库错误"),


    ATTRIBUTE_DELETE_ERROR_DB(HttpStatus.OK, 14021, "属性删除失败-数据库错误"),


    ATTRIBUTE_UPDATE_ERROR_DB(HttpStatus.OK, 14031, "属性更新失败-数据库错误"),

    /**
     * 属性值错误码
     */


    ATTRIBUTE_VALUE_CREATE_ERROR_DB(HttpStatus.OK, 15011, "属性值创建失败-数据库错误"),

    ATTRIBUTE_VALUE_DELETE_ERROR_DB(HttpStatus.OK, 15021, "属性值删除失败-数据库错误"),

    ATTRIBUTE_VALUE_UPDATE_ERROR_DB(HttpStatus.OK, 15031, "属性值更新失败-数据库错误"),

    PRODUCT_SERVICE_EXCEPTION(HttpStatus.OK, 19999, "其他异常 (测试)")
    ;

    private final HttpStatus httpStatus;
    private final Integer id;
    private final String message;
    ProductCodeEnum(HttpStatus httpStatus, Integer id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }
}
