package com.cswap.common.domain.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通用分页查询")
public class GenericPageQuery extends PageQuery {
    @Schema(description = "查询关键字")
    private String keyword;
}
