package com.cswap.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cswap.common.domain.page.GenericPageQuery;
import com.cswap.product.model.dto.AttributeDto;
import com.cswap.product.model.po.Attribute;

import java.util.List;

public interface IAttributeService extends IService<Attribute> {

    Page<Attribute> pageAttribute(GenericPageQuery query);

    boolean saveAttribute(AttributeDto attributeDto);

    boolean updateAttribute(AttributeDto attributeDto);

    List<AttributeDto> listByCategoryId(Long categoryId);
}
