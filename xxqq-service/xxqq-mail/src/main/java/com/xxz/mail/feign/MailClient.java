package com.xxz.mail.feign;

import com.xxz.apis.mail.IMailClient;
import com.xxz.mail.service.UserMailService;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.auth.dtos.MailVerificationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xzxie
 * @create 2023/11/10 16:55
 */
@RestController
@Slf4j
@Api(tags = "邮件服务接口")
public class MailClient implements IMailClient {

    @Autowired
    private UserMailService mailService;

    /**
     * 发送验证码
     * @param dto 邮箱地址和验证码
     * @return 返回发送结果
     */
    @ApiOperation("发送验证码")
    @PostMapping("/send/code")
    @Override
    public ResponseResult sendCode(@RequestBody MailVerificationDTO dto) {
        return mailService.sendCode(dto);
    }

}
