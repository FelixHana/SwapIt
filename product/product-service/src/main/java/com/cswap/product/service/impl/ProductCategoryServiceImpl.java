package com.cswap.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.product.mapper.ProductCategoryMapper;
import com.cswap.product.model.dto.ProductCategoryTreeDto;
import com.cswap.product.model.po.ProductCategory;
import com.cswap.product.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zcy
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory>
        implements IProductCategoryService {
    private final ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategoryTreeDto> queryTreeNodes(String categoryId) {
        List<ProductCategoryTreeDto> tmpList = productCategoryMapper.selectTreeNodes(categoryId);
        Map<String, ProductCategoryTreeDto> productCategoryTreeDtoMap = tmpList.stream()
                .filter(item -> !categoryId.equals(item.getCategoryId()))
                .collect(Collectors.toMap(ProductCategory::getCategoryId, value -> value, (key1, key2) -> key2));
        List<ProductCategoryTreeDto> resultList = new ArrayList<>();
        tmpList.stream().filter(item -> !categoryId.equals(item.getCategoryId())).forEach(item -> {
            if (item.getParentId().equals(categoryId)) {
                resultList.add(item);
            }
            ProductCategoryTreeDto parentNode = productCategoryTreeDtoMap.get(item.getParentId());
            if (parentNode != null) {
                if (parentNode.getChildrenNodes() == null) {
                    parentNode.setChildrenNodes(new ArrayList<>());
                }
                parentNode.getChildrenNodes().add(item);
            }

        });
        return resultList;
    }
}
