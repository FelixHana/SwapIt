package com.cswap.product.api;



import com.cswap.common.utils.BeanUtils;
import com.cswap.product.model.dto.CategoryBrandDto;
import com.cswap.product.service.ICategoryBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/category-brand")
@Slf4j
@Tag(name = "分类-品牌关联接口")
@RequiredArgsConstructor
public class CategoryBrandController {
    private final ICategoryBrandService categoryBrandService;

    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public boolean test() {
        return true;
    }

//    @Operation(summary = "分页查询分类-品牌关联")
//    @PostMapping("/list")
//    public PageDTO<Brand> page(@RequestBody GenericPageQuery query) {
//        Page<CategoryBrand> page = categoryBrandService.pageCategoryBrand(query);
//        return PageDTO.of(page, Brand.class);
//    }

    @Operation(summary = "查询品牌所有关联分类")
    @GetMapping("/list/category")
    public List<CategoryBrandDto> listCategoryByBrandId(@RequestParam("brandId") Long brandId) {
        return categoryBrandService.listByBrandId(brandId);
    }

    @Operation(summary = "查询分类所有关联品牌")
    @GetMapping("/list/brand")
    public List<CategoryBrandDto> listBrandByCategoryId(@RequestParam("categoryId") Long categoryId) {
        return categoryBrandService.listByCategoryId(categoryId);
    }

    @Operation(summary = "查询分类-品牌关联")
    @GetMapping
    public CategoryBrandDto query(@RequestParam("id") Long id) {
        return BeanUtils.copyBean(categoryBrandService.getById(id), CategoryBrandDto.class);
    }

    @Operation(summary = "新建分类-品牌关联")
    @PostMapping
    public boolean save(@RequestBody CategoryBrandDto categoryBrandDto) {
        return categoryBrandService.saveCategoryBrandRelation(categoryBrandDto);
    }

    @Operation(summary = "删除分类-品牌关联")
    @DeleteMapping
    public boolean delete(@RequestBody Long[] ids) {
        return categoryBrandService.removeByIds(Arrays.asList(ids));
    }



}
