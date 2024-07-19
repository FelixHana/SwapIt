package com.place.service;

import com.place.domain.po.PageBean;
import com.place.domain.dto.RequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface RequestService {
    /**
     * @param page         页码
     * @param pageSize     每页记录数
     * @param requestUser  请求用户
     * @param requestTitle 标题
     * @param requestType  类型
     * @param end          截止时间
     * @return pageBean 对象
     */
    PageBean page(Integer page, Integer pageSize,
                  String requestUser, String requestTitle,
                  Integer requestType, Integer requestState, LocalDate start,
                  LocalDate end, Integer role, boolean needLocation);

    /**
     * @param ids 删除请求id
     */
    void delete(List<Long> ids);

    /**
     * @param requestDTO 请求对象
     */
    void add(RequestDTO requestDTO);

    /**
     * @param requestDTO 修改后的对象
     */
    void edit(RequestDTO requestDTO);

    Boolean checkUser(String username, Long requestId);
}
