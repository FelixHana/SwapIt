package com.cswap.captcha.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import com.cswap.captcha.model.CaptchaParamsDto;
import com.cswap.captcha.model.CaptchaResultDto;
import com.cswap.captcha.service.AbstractCaptchaService;
import com.cswap.captcha.service.CaptchaService;
import com.cswap.common.constant.CaptchaConstants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service("PicCaptchaService")
@RequiredArgsConstructor
public class PicCaptchaServiceImpl extends AbstractCaptchaService implements CaptchaService {
    private final DefaultKaptcha kaptcha;

    @Resource(name="NumberLetterCaptchaGenerator")
    @Override
    public void setCaptchaGenerator(CaptchaGenerator captchaGenerator) {
        this.captchaGenerator = captchaGenerator;
    }

    @Resource(name="UUIDKeyGenerator")
    @Override
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }


    @Resource(name="RedisCaptchaStore")
    @Override
    public void setCaptchaStore(CaptchaStore captchaStore) {
        this.captchaStore = captchaStore;
    }


    @Override
    public CaptchaResultDto generate(CaptchaParamsDto captchaParamsDto) {
        GenerateResult generate = generate(captchaParamsDto,
                CaptchaConstants.CAPTCHA_CODE_LENGTH, CaptchaConstants.CAPTCHA_KEY_PREFIX, CaptchaConstants.CAPTCHA_EXPIRE_TIME);
        String key = generate.getKey();
        String code = generate.getCode();
        String pic = createPic(code);
        CaptchaResultDto captchaResultDto = new CaptchaResultDto();
        captchaResultDto.setAliasing(pic);
        captchaResultDto.setKey(key);
        return captchaResultDto;

    }

    private String createPic(String code) {
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = kaptcha.createImage(code);

        outputStream = new ByteArrayOutputStream();
        String imgBase64Encoder = null;
        try {
            // 对字节数组Base64编码
            ImageIO.write(image, "png", outputStream);
            imgBase64Encoder = "data:image/png;base64," + Base64Encoder.encode(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgBase64Encoder;
    }
}
