package com.cswap.mapper;

import com.cswap.domain.po.Request;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface RequestMapper {
    // 查询用户发布的请求信息
    List<Request> page(String userRequest, String requestTitle,
                       Integer requestType, Integer requestState, LocalDate start,
                       LocalDate end, Integer role, Integer location);

    // 删除用户发表的请求信息
    void delete(List<Long> ids);

    void add(Request request);
    void edit(Request request);
    void changeState(Long id, Integer state);

    String getUsernameById(Long id);

    String getLocation(Long id);

    Integer getType(Long id);
}
