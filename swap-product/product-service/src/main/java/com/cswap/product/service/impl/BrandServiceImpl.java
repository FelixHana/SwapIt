package com.cswap.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.domain.page.GenericPageQuery;
import com.cswap.common.utils.BeanUtils;
import com.cswap.product.mapper.BrandMapper;
import com.cswap.product.mapper.CategoryBrandMapper;
import com.cswap.product.model.dto.BrandDto;
import com.cswap.product.model.exception.ProductException;
import com.cswap.product.model.po.Brand;
import com.cswap.product.service.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cswap.product.model.enums.ProductCodeEnum.*;


@Service
@RequiredArgsConstructor
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    private final CategoryBrandMapper categoryBrandMapper;

    @Override
    public Page<Brand> pageBrand(GenericPageQuery query) {
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        String keyword = query.getKeyword();
        if (!StrUtil.isEmpty(keyword)) {
            wrapper.eq(Brand::getId, keyword).or().like(Brand::getName, keyword);
        }
        return this.page(query.pageSortDesc(), wrapper);
    }

    @Override
    public boolean toggleStatus(Long id) {
        Brand brand = this.getById(id);
        if (brand == null) {
            throw new ProductException(BRAND_NOT_EXIST);
        }
        boolean newStatus = !brand.getStatus();
        boolean updated = this.lambdaUpdate()
                .set(Brand::getStatus, newStatus)
                .eq(Brand::getId, id)
                .update();
        if (!updated) {
            throw new ProductException(BRAND_UPDATE_ERROR_DB);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBrand(BrandDto brandDto) {
        Brand old = this.getById(brandDto.getId());
        if (old == null) {
            throw new ProductException(BRAND_NOT_EXIST);
        }
        boolean updated = this.updateById(BeanUtils.copyBean(brandDto, Brand.class));
        if (!updated) {
            throw new ProductException(BRAND_UPDATE_ERROR_DB);
        }
        // 更新 CategoryBrand 中的 brand_name 冗余字段(若有)
        categoryBrandMapper.updateByBrandId(brandDto.getId(), brandDto.getName());
        // TODO 其他级联更新字段
        return true;
    }

    @Override
    public boolean saveBrand(BrandDto brandDto) {
        Brand brand = BeanUtils.copyProperties(brandDto, Brand.class);
        boolean saved = this.save(brand);
        if (!saved) {
            throw new ProductException(BRAND_CREATE_ERROR_DB);
        }
        return true;
    }
}
