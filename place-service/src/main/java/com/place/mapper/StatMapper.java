package com.place.mapper;

import com.place.domain.po.Stat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatMapper {
    List<Stat> getStat(String start, String end, String region);

    void update();
}
