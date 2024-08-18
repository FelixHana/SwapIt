package com.cswap.mapper;

import com.cswap.domain.po.TypeOption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {

    List<TypeOption> getAll();

}
