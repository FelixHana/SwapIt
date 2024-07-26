package com.place.controller;


import com.place.domain.dto.RequestDTO;
import com.place.service.RequestService;
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
@Api(tags = "用户需求相关接口")
@RequestMapping("/api/request")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    private final UserService userService;

    @ApiOperation("分页查询需求接口")
    @GetMapping
    public void page(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               String requestTitle,
                               Integer requestType,
                               Integer requestState,
                               @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate start,
                               @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate end) {
        log.info("requestList: page:{}, pageSize:{}, title:{}, type:{}, end:{}", page, pageSize, requestTitle, requestType, end);
        // TODO request page
        //        Integer role = Integer.parseInt(JwtUtil.getRoleFromToken(token));
        //        PageBean pageBean = requestService.page(page, pageSize, username, requestTitle, requestType, requestState, start, end, role, false);
        //return ;


    }

    @ApiOperation("分页查询同地区需求接口")
    @GetMapping("/res")
    public void pageForRes(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "5") Integer pageSize,
                                     String requestTitle,
                                     Integer requestType,
                                     @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate start,
                                     @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate end) {
//        TODO located request page
//        Integer role = Integer.parseInt(JwtUtil.getRoleFromToken(token));
//        PageBean pageBean = requestService.page(page, pageSize, username, requestTitle, requestType, null, start, end, role, true);
        //return
    }

    @ApiOperation("新建需求接口")
    @PostMapping("/add")
    public void add(@RequestBody RequestDTO requestDTO) {
        // TODO request add
//        requestDTO.setUsername(username);
//        requestService.add(requestDTO);
        //return
    }

    @ApiOperation("编辑需求接口")
    @PostMapping("/edit")
    public void edit(@RequestBody RequestDTO requestDTO) {
//        TODO request edit
//        if (!requestService.checkUser(username, requestDTO.getId())) {
//            return Result.error("USER_ERROR");
//        }
//        requestService.edit(requestDTO);
        //return
    }

    @ApiOperation("删除需求接口")
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable List<Long> ids) {

/*        TODO request delete
if (1 != role) {
            for (Long id : ids) {
                if (!requestService.checkUser(username, id)) {
                    return Result.error("USER_ERROR");
                }
            }
        }*/

        requestService.delete(ids);
        //return
    }


}
