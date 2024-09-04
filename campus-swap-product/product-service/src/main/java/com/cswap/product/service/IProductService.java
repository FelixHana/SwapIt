package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.product.model.dto.EditProductDto;
import com.cswap.common.domain.enums.ProductStatus;
import com.cswap.product.model.po.Product;
import com.cswap.product.model.query.ProductQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 */
public interface IProductService extends IService<Product> {

    ProductDto updateProduct(EditProductDto editProductDTO);

    Long createProduct(ProductDto productDTO);

    Page<Product> pageProduct(ProductQuery query);

    Boolean updateProductStatus(Long productId, ProductStatus productStatus);

    Boolean deleteProduct(Long productId);

    ProductDto toggleProductStatus(Long productId);

    ProductDto queryProduct(Long productId);
}
