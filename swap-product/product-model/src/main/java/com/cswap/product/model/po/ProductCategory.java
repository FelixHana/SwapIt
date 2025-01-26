package com.cswap.product.model.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcy
 * @since 2024-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_category")
@Schema(name="商品类目对象")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "category_id")
    private Long categoryId;

    @TableField("category_name")
    @NotEmpty
    private String categoryName;

    @TableField("parent_id")
    private Long parentId;

    @TableField("is_leaf")
    private Boolean isLeaf;

    @TableField("sort")
    private Integer sort;

    @TableLogic
    private Boolean deleted;


}
