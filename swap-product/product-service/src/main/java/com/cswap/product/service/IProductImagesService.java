package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.product.model.po.ProductImages;

import java.util.List;

public interface IProductImagesService extends IService<ProductImages> {

    boolean saveImages(List<String> images, Long productId);
}
