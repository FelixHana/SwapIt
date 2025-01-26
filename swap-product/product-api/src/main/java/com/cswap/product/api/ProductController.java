package com.cswap.product.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cswap.common.annotation.InternalApi;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.page.PageDTO;
import com.cswap.common.utils.UserContext;
import com.cswap.product.model.dto.EditProductDto;
import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.product.model.po.Product;
import com.cswap.product.model.query.ProductQuery;
import com.cswap.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author ZCY-
 */
@RestController
@Slf4j
@Tag(name = "商品接口")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public String test() {
        return "received!";
    }

    @Operation(summary = "分页查询商品接口")
    @PostMapping("/list")
    public PageDTO<ProductDto> page(@RequestBody ProductQuery query) {
        log.info(String.valueOf(UserContext.getUser()));
        Page<Product> result = productService.pageProduct(query);
        return PageDTO.of(result, ProductDto.class);
    }

    @InternalApi
    @Operation(summary = "查询商品接口 Internal")
    @GetMapping
    public ProductDto queryProductById(@RequestParam Long productId) {
        return productService.queryProduct(productId);
    }

    @Operation(summary = "新增商品")
    @PostMapping
    public Long saveProduct(@RequestBody @Validated EditProductDto editProductDto) {
        return productService.saveProduct(editProductDto);
    }

    @Operation(summary = "删除商品")
    @DeleteMapping
    public Boolean deleteProduct(@RequestParam Long productId) {
        return productService.deleteProduct(productId);
    }

    @Operation(summary = "修改商品信息")
    @PutMapping()
    public ProductDto editProduct(@RequestBody @Validated EditProductDto editProductDTO) {
        return productService.updateProduct(editProductDTO);
    }

    @InternalApi
    @Operation(summary = "锁定商品(售出) Internal")
    @PutMapping("/lock")
    public Boolean orderLockProduct(@RequestParam Long productId) {
        return productService.orderLockProduct(productId);
    }

    @Operation(summary = "切换商品可售/下架状态")
    @PutMapping("/toggle")
    public ProductDto toggleProductStatus(@RequestParam Long productId) {
        return productService.toggleProductStatus(productId);
    }


}
