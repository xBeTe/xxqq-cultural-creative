package com.xxz.apis.mail;

import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.auth.dtos.MailVerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 邮件服务的feign接口
 *
 * @author xzxie
 * @create 2023/11/10 16:53
 */
@FeignClient(value = "xxqq-mail")
public interface IMailClient {


    /**
     * 发送验证码
     * @param dto 邮箱和验证码
     * @return 返回发送结果
     */
    @PostMapping("/send/code")
    ResponseResult sendCode(@RequestBody MailVerificationDTO dto);
}
