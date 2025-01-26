package com.cswap.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cswap.common.domain.page.GenericPageQuery;
import com.cswap.common.utils.BeanUtils;
import com.cswap.product.mapper.AttributeMapper;
import com.cswap.product.model.dto.AttributeDto;
import com.cswap.product.model.dto.CategoryBrandDto;
import com.cswap.product.model.enums.ProductCodeEnum;
import com.cswap.product.model.exception.ProductException;
import com.cswap.product.model.po.Attribute;
import com.cswap.product.model.po.Brand;
import com.cswap.product.model.po.CategoryBrand;
import com.cswap.product.service.IAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements IAttributeService {

    @Override
    public Page<Attribute> pageAttribute(GenericPageQuery query) {
        LambdaQueryWrapper<Attribute> wrapper = new LambdaQueryWrapper<>();
        String keyword = query.getKeyword();
        if (!StrUtil.isEmpty(keyword)) {
            wrapper.eq(Attribute::getId, keyword).or().like(Attribute::getName, keyword);
        }
        return this.page(query.pageUpdateTimeDesc(), wrapper);
    }

    @Override
    public boolean saveAttribute(AttributeDto attributeDto) {
        boolean saved = this.save(BeanUtils.copyBean(attributeDto, Attribute.class));
        if (!saved) {
            throw new ProductException(ProductCodeEnum.ATTRIBUTE_CREATE_ERROR_DB);
        }
        return true;
    }

    @Override
    public boolean updateAttribute(AttributeDto attributeDto) {
        Attribute old = this.getById(attributeDto.getId());
        if (old == null) {
            throw new ProductException(ProductCodeEnum.ATTRIBUTE_NOT_EXIST);
        }
        boolean updated = this.updateById(BeanUtils.copyBean(attributeDto, Attribute.class));
        if (!updated) {
            throw new ProductException(ProductCodeEnum.ATTRIBUTE_UPDATE_ERROR_DB);
        }
        // TODO 修改属性名是否级联修改value表
        return true;
    }

    @Override
    public List<AttributeDto> listByCategoryId(Long categoryId) {
        List<Attribute> list = this.lambdaQuery().eq(Attribute::getCategoryId, categoryId).list();
        return BeanUtils.copyList(list, AttributeDto.class);
    }
}
