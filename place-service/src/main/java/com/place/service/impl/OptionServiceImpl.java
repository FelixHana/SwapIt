package com.place.service.impl;

import com.place.domain.po.TypeOption;
import com.place.mapper.OptionMapper;
import com.place.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OptionServiceImpl implements OptionService {
    private final OptionMapper optionMapper;

    public OptionServiceImpl(OptionMapper optionMapper) {
        this.optionMapper = optionMapper;
    }
    @Override
    public List<TypeOption> getAll() {
        return optionMapper.getAll();
    }
}
