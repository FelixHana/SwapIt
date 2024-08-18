package com.cswap.product.api;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cswap.common.annotation.InternalApi;
import com.cswap.common.domain.PageDTO;
import com.cswap.product.model.dto.EditProductDTO;
import com.cswap.product.model.dto.ProductDTO;
import com.cswap.product.model.enums.ProductStatus;
import com.cswap.product.model.po.Product;
import com.cswap.product.model.query.ProductQuery;
import com.cswap.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author ZCY-
 */
@RestController
@Tag(name = "商品接口")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @Operation(summary = "测试接口方法")
    @GetMapping("/test")
    public String test() {
        return "received!";
    }

    @Operation(summary = "分页查询商品接口")
    @PostMapping("/list")
    public PageDTO<ProductDTO> page(@RequestBody ProductQuery query) {
        Page<Product> result = productService.pageProduct(query);
        return PageDTO.of(result, ProductDTO.class);
    }

    @Operation(summary = "新增商品")
    @PostMapping
    public Long saveProduct(@RequestBody @Validated ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @Operation(summary = "删除商品")
    @DeleteMapping
    public Long deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return productId;
    }

    @Operation(summary = "修改商品信息")
    @PutMapping()
    public ProductDTO editProduct(@RequestBody @Validated EditProductDTO editProductDTO) {
        return productService.updateProduct(editProductDTO);
    }


    // 内部接口 productStatus只接受唯一枚举名作为参数 (不是JSON反序列化)
    @InternalApi
    @Operation(summary = "修改商品状态")
    @PutMapping("/status")
    public ProductDTO editProductStatus(@RequestParam Long productId, @RequestParam ProductStatus productStatus) {
        return productService.updateProductStatus(productId, productStatus);
    }

    @Operation(summary = "切换商品可售/下架状态")
    @PutMapping("/toggle")
    public ProductDTO toggleProductStatus(@RequestParam Long productId) {
        return productService.toggleProductStatus(productId);
    }


}
