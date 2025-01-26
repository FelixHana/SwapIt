package com.cswap.comment.api;


import com.cswap.comment.model.dto.LikeDto;
import com.cswap.comment.service.ILikeRecordService;
import com.cswap.common.domain.response.ResultResponse;
import com.cswap.common.utils.UserContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author ZCY-
 */
@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Tag(name = "点赞接口")
public class LikeRecordController {
    private final ILikeRecordService likeRecordService;

    @PostMapping
    @Tag(name = "点赞")
    public Boolean like(@RequestBody LikeDto likeDto) {
        likeRecordService.like(UserContext.getUser(), likeDto);
        return true;
    }

}
