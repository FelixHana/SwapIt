package com.cswap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.cswap.domain.po.PageBean;
import com.cswap.mapper.RequestMapper;
import com.cswap.mapper.UserMapper;
import com.cswap.domain.po.Request;
import com.cswap.domain.dto.RequestDTO;
import com.cswap.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestMapper requestMapper;

    private final UserMapper userMapper;


    @Override
    public PageBean page(Integer page, Integer pageSize,
                         String userRequest, String requestTitle,
                         Integer requestType, Integer requestState, LocalDate start,
                         LocalDate end, Integer role, boolean needLocation) {

        // 执行分页查询
        List<Request> requestList;
        if (needLocation) {
            Integer location = Integer.parseInt(userMapper.getLocationByUsername(userRequest));
            PageHelper.startPage(page, pageSize);
            requestList = requestMapper.page(userRequest, requestTitle, requestType, requestState, start, end, role, location);
        } else {
            PageHelper.startPage(page, pageSize);
            requestList = requestMapper.page(userRequest, requestTitle, requestType, requestState, start, end, role, null);
        }
        // 获取分页结果
        Page<Request> p = (Page<Request>) requestList;
        //封装PageBean
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Long> ids) {
        requestMapper.delete(ids);
    }

    @Override
    public void add(RequestDTO requestDTO) {
        Request request = new Request();
        request.setCreateTime(LocalDateTime.now());
        request.setUpdateTime(LocalDateTime.now());
        request.setEndTime(requestDTO.getDate().atStartOfDay());
        request.setRequestType(requestDTO.getType());
        request.setRequestTitle(requestDTO.getTitle());
        request.setRequestDetail(requestDTO.getDetail());
        request.setUserRequest(requestDTO.getUsername());
        request.setRequestFile(requestDTO.getFile());
        if(requestDTO.getMaxCostEnable()) {
            request.setMaxCost(requestDTO.getMaxCost());
        }
        request.setLocation(userMapper.getLocationByUsername(request.getUserRequest()));
        requestMapper.add(request);
    }

    @Override
    public void edit(RequestDTO requestDTO) {
        Request request = new Request();
        request.setId(requestDTO.getId());
        request.setUpdateTime(LocalDateTime.now());
        request.setEndTime(requestDTO.getDate().atStartOfDay());
        request.setRequestType(requestDTO.getType());
        request.setRequestTitle(requestDTO.getTitle());
        request.setRequestDetail(requestDTO.getDetail());
        request.setUserRequest(requestDTO.getUsername());
        request.setRequestFile(requestDTO.getFile());
        if(requestDTO.getMaxCostEnable()) {
            request.setMaxCost(requestDTO.getMaxCost());
        }
        requestMapper.edit(request);
    }

    @Override
    public Boolean checkUser(String username, Long requestId) {
        return requestMapper.getUsernameById(requestId).equals(username);
    }
}
