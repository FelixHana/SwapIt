package com.place.mapper;

import com.place.domain.po.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    void create(Order order);
}
