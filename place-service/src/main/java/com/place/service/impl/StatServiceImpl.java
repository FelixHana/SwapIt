package com.place.service.impl;

import com.place.domain.po.Stat;
import com.place.mapper.StatMapper;
import com.place.service.StatService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {

    private StatMapper statMapper;

    public StatServiceImpl(StatMapper statMapper) {
        this.statMapper = statMapper;
    }

    @Override
    public List<Stat> getStat(LocalDate start, LocalDate end, String region) {
        statMapper.update();
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        String startMonth = null, endMonth = null;
        if (start != null) {
            startMonth = start.format(customFormatter);
        }
        if (end != null) {
            endMonth = end.format(customFormatter);
        }
        return statMapper.getStat(startMonth, endMonth, region);
    }
}
