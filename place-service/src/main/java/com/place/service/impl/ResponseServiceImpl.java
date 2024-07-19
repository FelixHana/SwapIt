package com.place.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.place.domain.po.Order;
import com.place.domain.po.PageBean;
import com.place.service.ResponseService;
import com.place.domain.dto.ResponseDTO;
import com.place.domain.po.Response;
import com.place.mapper.OrderMapper;
import com.place.mapper.RequestMapper;
import com.place.mapper.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {
    private final ResponseMapper responseMapper;
    private final RequestMapper requestMapper;
    private final OrderMapper orderMapper;

    public ResponseServiceImpl(ResponseMapper responseMapper, RequestMapper requestMapper, OrderMapper orderMapper) {
        this.responseMapper = responseMapper;
        this.requestMapper = requestMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public void add(ResponseDTO responseDTO) {
        Response response = new Response();
        response.setUserResponse(responseDTO.getUserResponse());
        response.setResponseTitle(responseDTO.getTitle());
        response.setResponseDetail(responseDTO.getDetail());
        response.setScheduleDate(responseDTO.getDate());
        response.setResponseFile(responseDTO.getResponseFile());
        response.setRequestId(responseDTO.getRequestId());
        response.setCost(responseDTO.getCost());
        response.setCreateTime(LocalDateTime.now());
        response.setUpdateTime(LocalDateTime.now());
        responseMapper.add(response);
        requestMapper.changeState(response.getRequestId(), 1);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, Long requestId, Integer role) {
        // 执行分页查询
        List<Response> responseList;
        PageHelper.startPage(page, pageSize);
        responseList = responseMapper.page(page, pageSize, requestId, role);
        // 获取分页结果
        Page<Response> p = (Page<Response>) responseList;
        //封装PageBean
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String username,
                         String responseTitle, LocalDate start, LocalDate end, Integer role) {
        // 执行分页查询
        List<Response> responseList;
        PageHelper.startPage(page, pageSize);
        responseList = responseMapper.pageUserRes(username, responseTitle, start, end, role);
        // 获取分页结果
        Page<Response> p = (Page<Response>) responseList;
        //封装PageBean
        return new PageBean(p.getTotal(), p.getResult());
    }


    @Override
    public Long getRequestId(Long responseId) {
        return responseMapper.getRequestId(responseId);

    }

    @Override
    public void confirm(Long responseId, Long requestId) {
        responseMapper.confirm(responseId, requestId);
        requestMapper.changeState(requestId, 2);

        Order order = new Order(
                null,
                requestId,
                requestMapper.getUsernameById(requestId),
                responseMapper.getUsernameById(responseId),
                LocalDateTime.now(),
                2,
                2,
                responseId,
                requestMapper.getLocation(requestId),
                requestMapper.getType(requestId));
        orderMapper.create(order);
    }

    @Override
    public Boolean checkUser(String username, Long id) {
        return responseMapper.getUsernameById(id).equals(username);
    }

    @Override
    public void delete(List<Long> ids) {

        for (Long id: ids) {
            Long requestId = responseMapper.getRequestId(id);
            requestMapper.changeState(requestId, 0);
        }
        responseMapper.delete(ids);

    }

    @Override
    public void edit(ResponseDTO responseDTO) {
        Response response = new Response();
        response.setId(responseDTO.getId());
        response.setUserResponse(responseDTO.getUserResponse());
        response.setResponseTitle(responseDTO.getTitle());
        response.setResponseDetail(responseDTO.getDetail());
        response.setScheduleDate(responseDTO.getDate());
        response.setResponseFile(responseDTO.getResponseFile());
        response.setCost(responseDTO.getCost());
        response.setRequestId(responseDTO.getRequestId());
        response.setUpdateTime(LocalDateTime.now());
        responseMapper.edit(response);
    }

}
