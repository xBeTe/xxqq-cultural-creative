package com.xxz.mail.test;

import com.xxz.mail.service.SendService;
import com.xxz.mail.service.UserMailService;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.mail.dtos.ToMailDTO;
import com.xxz.model.auth.dtos.MailVerificationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xzxie
 * @create 2023/11/10 21:27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SendMailTest {

    @Autowired
    private SendService sendService;

    @Test
    public void testSendSimpleMail() {
        ToMailDTO toMail = ToMailDTO.builder().subject("测试邮件").content("测试邮件内容").build();
        toMail.setSingleTo("bete1031@qq.com");
        ResponseResult result = sendService.sendSimpleMail(toMail);
        System.out.println(result);
    }

    @Autowired
    private UserMailService userMailService;

    @Test
    public void testSendCode() {
        ResponseResult responseResult = userMailService.sendCode(MailVerificationDTO.builder().email("bete1031@qq.com").code("123456").build());
        System.out.println(responseResult);
    }
}
