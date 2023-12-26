package com.xxz.mail.service.impl;

import com.xxz.mail.service.SendService;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import com.xxz.model.mail.dtos.ToMailDTO;
import com.xxz.common.constants.MailConstants;
import com.xxz.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * @author xzxie
 * @create 2023/11/10 20:27
 */
@Service
@Slf4j
public class SendMailImpl implements SendService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     *
     * @param to 收件基础信息
     */
    @Override
    public ResponseResult sendSimpleMail(ToMailDTO to) {
        try {
            // 校验参数
            check(to);
            // 创建简单邮件
            SimpleMailMessage message = new SimpleMailMessage();
            // 设置发件人邮箱
            message.setFrom(getMailFrom());
            // 设置收件人邮箱
            message.setTo(to.getTosArray());
            // 设置邮件标题
            message.setSubject(to.getSubject());
            // 设置邮件内容
            message.setText(to.getContent());
            // 发送邮件
            mailSender.send(message);
        } catch (MailException e) {
            return ErrorResponseResult.result(HttpCodeEnum.MAIL_SEND_FAIL);
        } catch (CustomException e) {
            return ErrorResponseResult.result(e.getHttpCodeEnum());
        }
        return OkResponseResult.result();


    }

    /**
     * 发送带附件的邮件
     *
     * @param to   收件基础信息
     * @param fileUrl 附件
     * @param fileName 附件名称
     */
    @Override
    public ResponseResult sendAttachFileMail(ToMailDTO to, String fileUrl, String fileName) {
        try {
            check(to);

            // 创建一个 MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            // 设置发件人邮箱
            messageHelper.setFrom(getMailFrom());
            // 设置收件人邮箱
            messageHelper.setTo(to.getTosArray());
            // 设置邮件标题
            messageHelper.setSubject(to.getSubject());
            // 设置邮件内容
            messageHelper.setText(to.getContent());
            // 添加附件
            messageHelper.addAttachment(fileName, getFile(fileUrl));
            // 发送邮件
            mailSender.send(message);

        } catch (MessagingException e) {
            return ErrorResponseResult.result(HttpCodeEnum.MAIL_SEND_FAIL);
        } catch (CustomException e) {
            return ErrorResponseResult.result(e.getHttpCodeEnum());
        }
        return OkResponseResult.result();
    }

    /**
     * 从文件服务器获取附件
     * @param fileUrl 附件地址
     * @return 附件
     */
    private File getFile(String fileUrl) {
        // TODO 从文件服务器获取附件
        return null;
    }

    /**
     * 发送 html 邮件
     *
     * @param to 收件基础信息
     */
    @Override
    public ResponseResult sendHtmlMail(ToMailDTO to) {
        try {
            check(to);
            // 创建一个 MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            // 设置发件人邮箱
            messageHelper.setFrom(getMailFrom());
            // 设置收件人邮箱
            messageHelper.setTo(to.getTosArray());
            // 设置邮件标题
            messageHelper.setSubject(to.getSubject());
            // 设置邮件内容
            messageHelper.setText(to.getContent(), true);
            // 发送邮件
            mailSender.send(message);

        } catch (MessagingException e) {
            return ErrorResponseResult.result(HttpCodeEnum.MAIL_SEND_FAIL);
        } catch (CustomException e) {
            return ErrorResponseResult.result(e.getHttpCodeEnum());
        }
        return OkResponseResult.result();
    }

    private String getMailFrom() {
        return MailConstants.MAIL_NICKNAME + "<" + username + ">";
    }

    private void check(ToMailDTO toMail) {
        if (toMail == null ) {
            throw new CustomException(HttpCodeEnum.PARAM_REQUIRE);
        }
        if (toMail.getTos() == null || toMail.getTos().isEmpty()) {
            throw new CustomException(HttpCodeEnum.MAIL_SEND_TO_REQUIRE);
        }
        if (toMail.getSubject() == null || StringUtils.isBlank(toMail.getSubject())) {
            throw new CustomException(HttpCodeEnum.MAIL_SEND_SUBJECT_REQUIRE);
        }
        if (toMail.getContent() == null || StringUtils.isBlank(toMail.getContent())) {
            throw new CustomException(HttpCodeEnum.MAIL_SEND_CONTENT_REQUIRE);
        }
    }


}
