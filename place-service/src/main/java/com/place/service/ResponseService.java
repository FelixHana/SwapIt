package com.place.service;

import com.place.domain.po.PageBean;
import com.place.domain.dto.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ResponseService {
    /**
     * @param responseDTO 请求表单
     */
    void add(ResponseDTO responseDTO);

    /**
     * 查询某个请求对应的所有响应
     * @param page 页码
     * @param pageSize 页大小
     * @param requestId 请求id
     * @param role 管理员
     * @return pageBean
     */
    PageBean page(Integer page, Integer pageSize, Long requestId, Integer role);

    /**
     * 查询某个用户的所有响应
     * @param page 页码
     * @param pageSize 页大小
     * @param username 用户名
     * @return pageBean
     */
    PageBean page(Integer page, Integer pageSize, String username,
                  String responseTitle, LocalDate start, LocalDate end, Integer role);

    /**
     * 响应id查请求id
     * @param responseId 响应id
     * @return 请求id
     */
    Long getRequestId(Long responseId);

    /**
     * 确认一个响应，拒绝对应请求的其他响应
     * @param responseId 响应id
     * @param requestId 请求id
     */
    void confirm(Long responseId, Long requestId);

    Boolean checkUser(String username, Long id);

    void delete(List<Long> ids);

    void edit(ResponseDTO responseDTO);
}
