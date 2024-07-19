package com.place.mapper;

import com.place.domain.po.TypeOption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {

    List<TypeOption> getAll();

}
