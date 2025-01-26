package com.cswap.product.model.dto;

import com.cswap.product.model.po.ProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZCY-
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductCategoryTreeDto extends ProductCategory implements Serializable {
    List<ProductCategoryTreeDto> childrenNodes;


}
