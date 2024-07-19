package com.place.mapper;

import com.place.domain.po.Response;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ResponseMapper {
    void add(Response response);

    List<Response> page(Integer page, Integer pageSize, Long requestId, Integer role);
    List<Response> pageUserRes(String username, String responseTitle, LocalDate start, LocalDate end, Integer role);
    Long getRequestId(Long responseId);

    void confirm(Long id, Long requestId);

    String getUsernameById(Long id);

    void delete(List<Long> ids);

    void edit(Response response);
}
