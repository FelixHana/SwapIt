package com.cswap.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.product.mapper.ProductImagesMapper;
import com.cswap.product.model.po.ProductImages;
import com.cswap.product.service.IProductImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImagesServiceImpl extends ServiceImpl<ProductImagesMapper, ProductImages> implements IProductImagesService {

    private final ProductImagesMapper productImagesMapper;

    @Override
    public boolean saveImages(List<String> images, Long productId) {
        if (images == null || images.isEmpty()) {
            return false;
        }
        List<ProductImages> imagesList = images.stream().map(image -> {
            ProductImages productImages = new ProductImages();
            productImages.setProductId(productId);
            productImages.setUrl(image);
            productImages.setDefaultImage(false);
            return productImages;
        }).collect(Collectors.toList());
        imagesList.get(0).setDefaultImage(true);
        boolean saved = productImagesMapper.saveImagesList(imagesList);
        return saved;
    }
}
