package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.common.domain.page.GenericPageQuery;
import com.cswap.product.model.dto.BrandDto;
import com.cswap.product.model.po.Brand;

public interface IBrandService extends IService<Brand> {

    Page<Brand> pageBrand(GenericPageQuery query);

    boolean toggleStatus(Long id);

    boolean updateBrand(BrandDto brandDto);

    boolean saveBrand(BrandDto brandDto);
}
