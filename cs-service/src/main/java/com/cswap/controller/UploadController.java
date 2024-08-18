package com.cswap.controller;


import com.cswap.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class UploadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/api/upload")
    public String upload(MultipartFile file) throws IOException {
        //调用阿里云OSS工具类，将上传上来的文件存入阿里云
        //将图片上传完成后的url返回，用于浏览器回显展示
        return aliOSSUtils.upload(file);
    }
}
