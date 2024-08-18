package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.product.model.dto.EditProductDTO;
import com.cswap.product.model.dto.ProductDTO;
import com.cswap.product.model.enums.ProductStatus;
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

    ProductDTO updateProduct(EditProductDTO editProductDTO);

    Long createProduct(ProductDTO productDTO);

    Page<Product> pageProduct(ProductQuery query);

    ProductDTO updateProductStatus(Long productId, ProductStatus productStatus);

    void deleteProduct(Long productId);

    ProductDTO toggleProductStatus(Long productId);
}
