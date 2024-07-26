package com.place.controller;


import com.place.domain.po.Stat;
import com.place.service.StatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/stat")
public class StatController {
    @Autowired
    private StatService statService;

    @GetMapping
    public List<Stat> getStat(@DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate start,
                              @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate end,
                              String region) {
        // TODO stat role
        return statService.getStat(start, end, region);

    }
}
