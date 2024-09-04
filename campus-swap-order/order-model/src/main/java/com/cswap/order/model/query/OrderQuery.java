package com.cswap.order.model.query;


import com.cswap.common.domain.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "订单分页查询DTO", description = "订单分页查询DTO")
public class OrderQuery extends PageQuery {

}
