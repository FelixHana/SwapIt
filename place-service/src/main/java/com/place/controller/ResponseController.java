package com.place.controller;

import com.place.common.domain.response.ResultResponse;
import com.place.domain.po.PageBean;
import com.place.domain.dto.ResponseDTO;
import com.place.service.RequestService;
import com.place.service.ResponseService;
import com.place.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "用户应答相关接口")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/response")
public class ResponseController {
    private final ResponseService responseService;

    private final RequestService requestService;

    private final UserService userService;

    @ApiOperation("新建应答接口")
    @PostMapping("/add")
    public void add(@RequestBody ResponseDTO responseDTO) {
        // TODO res add
/*        responseDTO.setUserResponse(username);
        responseService.add(responseDTO);*/
    }
    @ApiOperation("分页查询应答接口")
    @GetMapping
    public void page(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam Long requestId) {
        // TODO res page
        PageBean pageBean = responseService.page(page, 5, requestId, 1/* TODO role*/);
        return;
    }

    @ApiOperation("分页查询本用户应答接口")
    @GetMapping("/my")
    public void page(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               String responseTitle,
                               @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate start,
                               @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate end) {
        // TODO res my page
        // PageBean pageBean = responseService.page(page, pageSize, username, responseTitle, start, end, role);
        //return
    }
    @ApiOperation("确认应答接口")
    @GetMapping("/confirm")
    public void confirm(Long id) {
        // TODO res confirm
        Long requestId = responseService.getRequestId(id);
        if (requestId == null) {
            //throw new BusinessException("id 为空");
        }
        responseService.confirm(id, requestId);
        //return
    }
    @ApiOperation("删除应答接口")
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable List<Long> ids) {
        // TODO res delete
        responseService.delete(ids);
        //return
    }

    @ApiOperation("编辑应答接口")
    @PostMapping("/edit")
    public Long edit(@RequestBody ResponseDTO responseDTO) {
        // TODO res edit
        responseService.edit(responseDTO);
        return responseDTO.getId();
    }

}
