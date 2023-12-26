package com.xxz.model.mail.vos;

import lombok.Builder;
import lombok.Data;

/**
 * 邮箱验证页面视图对象
 *
 * @author xzxie
 * @create 2023/11/11 19:41
 */
@Data
@Builder
public class MailVerificationVO {

    /**
     * 邮箱验证消息内容
     */
    private String message;

    /**
     * 验证码有效期
     */
    private Integer validityPeriod;

    /**
     * 验证码：4 位随机数
     */
    private String code;

    /**
     * 业务网站的 URL
     */
    private String serviceUrl;


}
