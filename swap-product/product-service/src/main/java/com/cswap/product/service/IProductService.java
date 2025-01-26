package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.common.domain.dto.ProductDto;
import com.cswap.common.domain.mq.OrderTo;
import com.cswap.product.model.dto.EditProductDto;
import com.cswap.product.model.po.Product;
import com.cswap.product.model.query.ProductQuery;

public interface IProductService extends IService<Product> {

    ProductDto updateProduct(EditProductDto editProductDTO);

    Long saveProduct(EditProductDto productDTO);

    Page<Product> pageProduct(ProductQuery query);

    Boolean orderLockProduct(Long productId);

    Boolean deleteProduct(Long productId);

    ProductDto toggleProductStatus(Long productId);

    ProductDto queryProduct(Long productId);

    void unlockProduct(Long productId);

    void unlockProduct(OrderTo order);
}
