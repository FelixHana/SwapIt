package com.cswap.order.model.dto;

import com.cswap.common.domain.dto.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "订单确认DTO")
public class OrderConfirmDto {
    @Schema(description = "收货地址列表")
    List<AddressDto> addressList;

    @Schema(description = "商品信息")
    ProductDto product;

    @Schema(description = "订单token")
    private String orderToken;

}
