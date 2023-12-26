package com.xxz.common.constants;

/**
 * @author xzxie
 * @create 2023/11/10 20:47
 */
public class MailConstants {

    /**
     * 邮箱昵称
     */
    public static final String MAIL_NICKNAME = "小巷千秋文创商城";

    public static final String MAIL_VERIFICATION_CACHE_PREFIX = "mail:verification:";

    /**
     * 邮箱验证码邮箱时间：10分钟
     */
    public static final Long MAIL_VERIFICATION_EXPIRE = 60 * 10L;

    /**
     * 发送邮箱验证码的请求间隔时间：60秒
     */
    public static final Long MAIL_VERIFICATION_INTERVAL = 60 * 1000L;
    public static final String MAIL_VERIFICATION_SUBJECT = "小巷千秋文创商城验证码";
    public static final String MAIL_VERIFICATION_SERVICE_URL = "http://localhost:8080";
    public static final String MAIL_VERIFICATION_MESSAGE = "您正在尝试进行注册操作，若非是您本人的行为，请忽略!";
}
