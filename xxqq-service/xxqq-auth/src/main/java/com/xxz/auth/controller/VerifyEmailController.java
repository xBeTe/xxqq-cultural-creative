package com.xxz.auth.controller;

import com.xxz.auth.service.VerifyEmailService;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.auth.dtos.MailVerificationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xzxie
 * @create 2023/11/10 10:58
 */
@RestController
@Slf4j
@Api(tags = "邮箱验证接口")
@RequestMapping("/email")
public class VerifyEmailController {

    @Autowired
    private VerifyEmailService registerService;


    @ApiOperation("发送验证码")
    @GetMapping("/code/{email}")
    public ResponseResult sendCode(@PathVariable String email) {
        return registerService.sendCode(email);
    }

    @ApiOperation("验证邮箱")
    @PostMapping("/verify")
    public ResponseResult verify(@RequestBody MailVerificationDTO dto) {
        return registerService.verify(dto);
    }





}
