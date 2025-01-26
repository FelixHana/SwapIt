package com.cswap.captcha.controller;

import com.cswap.captcha.model.CaptchaParamsDto;
import com.cswap.captcha.model.CaptchaResultDto;
import com.cswap.captcha.service.CaptchaService;
import com.cswap.common.annotation.InternalApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@Tag(name = "验证码服务接口")
@RestController
public class CaptchaController {

    @Resource(name = "PicCaptchaService")
    private CaptchaService picCaptchaService;

    @Operation(summary = "生成验证信息")
    @PostMapping(value = "/pic")
    public CaptchaResultDto generatePicCaptcha(CaptchaParamsDto captchaParamsDto){
        return picCaptchaService.generate(captchaParamsDto);
    }

    @InternalApi
    @Operation(summary = "校验")
    @Parameters({
            @Parameter(name = "name", description = "业务名称", required = true),
            @Parameter(name = "key", description = "验证key", required = true),
            @Parameter(name = "code", description = "验证码", required = true)
    })
    @PostMapping(value = "/verify")
    public Boolean verify(String key, String code){
        return picCaptchaService.verify(key,code);
    }
}
