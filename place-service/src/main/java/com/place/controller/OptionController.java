package com.place.controller;

import com.place.domain.po.TypeOption;
import com.place.service.OptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
@Slf4j
public class OptionController {
    private final OptionService optionService;

    @ApiOperation("获取下拉选项接口")
    @GetMapping
    public List<TypeOption> getAll() {
        return optionService.getAll();
    }
}
