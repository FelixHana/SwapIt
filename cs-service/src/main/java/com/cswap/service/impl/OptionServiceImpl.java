package com.cswap.service.impl;

import com.cswap.domain.po.TypeOption;
import com.cswap.mapper.OptionMapper;
import com.cswap.service.OptionService;
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
