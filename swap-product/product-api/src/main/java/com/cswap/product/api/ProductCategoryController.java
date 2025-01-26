package com.cswap.product.api;

import com.cswap.product.model.dto.ProductCategoryTreeDto;
import com.cswap.product.model.po.ProductCategory;
import com.cswap.product.service.IProductCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZCY-
 */

@RestController
@RequestMapping("/category")
@Slf4j
@Tag(name = "商品类目接口")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final IProductCategoryService productCategoryService;


    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public String test() {
/*        log.info("redisson test********************");
        RLock lock = redisson.getLock("test");
        lock.lock();
        try{
            System.out.println("+locked" + Thread.currentThread().getId());
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            lock.unlock();
            System.out.println("-unlocked" + Thread.currentThread().getId());
        }*/
        return "received!";
    }

    @Operation(summary = "查询商品类目树形结果")
    @GetMapping("/list/all")
    public List<ProductCategoryTreeDto> queryAllCategories() throws JsonProcessingException {
        return productCategoryService.queryAllCategories();
    }

    @Operation(summary = "查询商品一级类目列表")
    @GetMapping("/list/root")
    public List<ProductCategory> queryRootCategories(){
        return productCategoryService.queryRootCategories();
    }


    @Operation(summary = "修改商品类目")
    @PutMapping
    public boolean update(@RequestBody ProductCategory productCategory) {
        return productCategoryService.updateCategory(productCategory);
    }

    @Operation(summary = "新增商品类目")
    @PostMapping
    public boolean add(@RequestBody ProductCategory productCategory) {
        return productCategoryService.createCategoryCascade(productCategory);
    }

    @Operation(summary = "删除商品类目")
    @DeleteMapping
    public boolean delete(@RequestBody Long[] ids) {
        return productCategoryService.removeCategoryByIds(ids);
    }
}
