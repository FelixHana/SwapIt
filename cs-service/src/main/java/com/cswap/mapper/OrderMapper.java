package com.cswap.mapper;

import com.cswap.domain.po.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    void create(Order order);
}
