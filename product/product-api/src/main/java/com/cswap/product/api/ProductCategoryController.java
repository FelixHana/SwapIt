package com.cswap.product.api;

import com.cswap.product.model.dto.ProductCategoryTreeDto;
import com.cswap.product.service.IProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZCY-
 */

@RestController
@Tag(name = "商品类目接口")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final IProductCategoryService productCategoryService;
    @Operation(summary = "查询商品类目树形结果")
    @GetMapping("/categories/list")
    public List<ProductCategoryTreeDto> queryCategories(@RequestParam String categoryId) {
        return productCategoryService.queryTreeNodes(categoryId);
    }
}
