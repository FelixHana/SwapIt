package com.cswap.product.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cswap.common.domain.page.GenericPageQuery;
import com.cswap.common.domain.page.PageDTO;
import com.cswap.product.model.dto.AttributeDto;
import com.cswap.product.model.po.Attribute;
import com.cswap.product.service.IAttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/attribute")
@Slf4j
@Tag(name = "属性接口")
@RequiredArgsConstructor
public class AttributeController {
    private final IAttributeService attributeService;

    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public boolean test() {
        return true;
    }

    @Operation(summary = "分页查询属性")
    @PostMapping("/list")
    public PageDTO<Attribute> page(@RequestBody GenericPageQuery query) {
        Page<Attribute> page = attributeService.pageAttribute(query);
        return PageDTO.of(page, Attribute.class);
    }

    @Operation(summary = "查询分类属性列表")
    @GetMapping("/list/category")
    public List<AttributeDto> listAttributeByCategoryId(@RequestParam("categoryId") Long categoryId) {
        return attributeService.listByCategoryId(categoryId);
    }



    @Operation(summary = "查询属性")
    @GetMapping
    public Attribute queryAttributeById(@RequestParam("id") Long id) {
        return attributeService.getById(id);
    }

    @Operation(summary = "新建属性")
    @PostMapping
    public boolean saveAttribute(@RequestBody AttributeDto attributeDto) {
        return attributeService.saveAttribute(attributeDto);
    }

    @Operation(summary = "删除属性")
    @DeleteMapping
    public boolean deleteAttributeByIds(@RequestBody Long[] ids) {
        return attributeService.removeByIds(Arrays.asList(ids));
    }

    @Operation(summary = "修改属性")
    @PutMapping
    public boolean updateAttribute(@RequestBody AttributeDto attributeDto) {
        return attributeService.updateAttribute(attributeDto);
    }
}
