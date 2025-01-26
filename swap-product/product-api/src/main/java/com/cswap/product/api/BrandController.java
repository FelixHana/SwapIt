package com.cswap.product.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cswap.common.domain.page.GenericPageQuery;
import com.cswap.common.domain.page.PageDTO;
import com.cswap.common.utils.BeanUtils;
import com.cswap.product.model.dto.BrandDto;
import com.cswap.product.model.po.Brand;
import com.cswap.product.service.IBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/brand")
@Slf4j
@Tag(name = "品牌接口")
@RequiredArgsConstructor
public class BrandController {
    private final IBrandService brandService;

    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public boolean test() {
        return true;
    }

    @Operation(summary = "分页查询品牌")
    @PostMapping("/list")
    public PageDTO<BrandDto> page(@RequestBody GenericPageQuery query) {
        Page<Brand> page = brandService.pageBrand(query);
        return PageDTO.of(page, BrandDto.class);
    }

    @Operation(summary = "查询品牌")
    @GetMapping
    public BrandDto queryBrandById(@RequestParam("id") Long id) {
        return BeanUtils.copyBean(brandService.getById(id), BrandDto.class);
    }

    @Operation(summary = "新建品牌")
    @PostMapping
    public boolean saveBrand(@RequestBody BrandDto brandDto) {
        return brandService.saveBrand(brandDto);
    }

    @Operation(summary = "删除品牌")
    @DeleteMapping
    public boolean deleteBrandByIds(@RequestBody Long[] ids) {
        return brandService.removeByIds(Arrays.asList(ids));
    }

    @Operation(summary = "修改品牌")
    @PutMapping
    public boolean updateBrand(@RequestBody BrandDto brandDto) {
        return brandService.updateBrand(brandDto);
    }

    @Operation(summary = "修改品牌状态")
    @PutMapping("/toggle")
    public boolean toggleBrandStatus(@RequestParam("id") Long id) {
        return brandService.toggleStatus(id);
    }


}
