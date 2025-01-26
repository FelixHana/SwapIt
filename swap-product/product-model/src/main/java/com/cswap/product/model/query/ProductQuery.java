package com.cswap.product.model.query;

import com.cswap.common.domain.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ZCY-
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "商品分页查询DTO", description = "商品分页查询DTO")
public class ProductQuery extends PageQuery {
    @Schema(description = "商品名")
    private String productName;
    @Schema(description = "商品类别")
    private Integer category;
    @Schema(description = "商品类别")
    private Integer productStatus;
    @Schema(description = "价格最高")
    private BigDecimal priceMax;
    @Schema(description = "创建时间起始值")
    private LocalDateTime createTimeBegin;
    @Schema(description = "创建时间结束值")
    private LocalDateTime createTimeEnd;

}
