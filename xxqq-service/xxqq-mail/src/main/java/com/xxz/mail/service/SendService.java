package com.xxz.mail.service;

import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.mail.dtos.ToMailDTO;

/**
 * @author xzxie
 * @create 2023/11/10 20:11
 */
public interface SendService {

    /**
     * 发送简单邮件
     * @param to 收件基础信息
     */
    ResponseResult sendSimpleMail(ToMailDTO to);

    /**
     * 发送带附件的邮件
     * @param to 收件基础信息
     * @param filePath 附件地址
     * @param fileName 附件名称
     */
    ResponseResult sendAttachFileMail(ToMailDTO to, String filePath, String fileName);

    /**
     * 发送 html 邮件
     * @param to 收件基础信息
     */
    ResponseResult sendHtmlMail(ToMailDTO to);
}
