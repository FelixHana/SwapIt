package com.cswap.common.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ZCY-
 */
@Data
@Accessors(chain = true)
@Schema(name = "商品图片对象")
public class ProductImage {
    @Schema(description = "商品图片数量")
    private int count;
    @Schema(description = "商品图片链接")
    private List<String> images;
}
