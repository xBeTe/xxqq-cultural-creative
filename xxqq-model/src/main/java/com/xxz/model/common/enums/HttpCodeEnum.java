package com.xxz.model.common.enums;

import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/11/6 15:23
 */
@Getter
public enum HttpCodeEnum {

    // 成功
    SUCCESS(200, "操作成功"),

    // 登录段：1 ~ 49
    NEED_LOGIN(1, "需要登录后操作"),
    LOGIN_FAIL(2, "登录失败，用户名或密码错误"),
    LOGIN_NAME_REQUIRE(3, "用户名/手机号/邮箱是必须的"),
    LOGIN_PASSWORD_REQUIRE(4, "密码是必须的"),
    LOGIN_USER_NOT_EXIST(5, "用户不存在"),
    REGISTER_EMAIL_REQUIRE(6, "邮箱是必须的"),
    REGISTER_PASSWORD_REQUIRE(7, "密码是必须的"),
    REGISTER_EMAIL_EXIST(8, "邮箱已经存在"),
    REGISTER_CODE_REQUIRE(9, "验证码是必须的"),
    REGISTER_CODE_EXPIRE(10, "验证码已过期"),
    REGISTER_CODE_ERROR(11, "验证码错误"),
    USERNAME_EXIST(12, "用户名已存在"),
    USERNAME_INVALID(13, "用户名不合法"),
    PASSWORD_INVALID(14, "密码不合法"),


    USER_ACCOUNT_EXPIRED(40, "账号已过期"),
    USER_ACCOUNT_LOCKED(41, "账号已被锁定"),
    USER_ACCOUNT_DISABLE(42, "账号已被禁用"),

    // TOKEN：50 ~ 99
    TOKEN_INVALID(50, "无效的 TOKEN"),
    TOKEN_EXPIRED(51, "TOKEN 已过期"),
    TOKEN_REQUIRE(52, "TOKEN 是必须的"),
    TOKEN_SIGNATURE_ERROR(53, "TOKEN 签名错误"),
    TOKEN_REFRESH_RETRY(60, "TOKEN 刷新，跟新 TOKEN 后重试"),

    // 参数错误：500 ~ 599
    PARAM_REQUIRE(500, "参数错误"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    PARAM_FILE_FORMAT_ERROR(503, "文件格式有误"),
    SERVER_ERROR(504, "服务器内部错误"),

    // 数据错误：1000 ~ 1099
    DATA_EXIST(1000, "数据已经存在"),
    DATA_NOT_EXIST(1001, "数据不存在"),
    DATA_SAVE_FAIL(1002, "数据保存失败"),
    USER_DATA_NOT_EXIST(1010, "User 数据不存在"),
    ADMIN_DATA_NOT_EXIST(1011, "Admin 数据不存在"),

    // 用户信息参数校验错误：1100 ~ 1199
    USER_NICKNAME_LEN_INVALID(1100, "用户昵称长度错误"),
    USER_NICKNAME_CONTENT_INVALID(1101, "用户昵称包含非法内容"),
    USER_SIGNATURE_LEN_INVALID(1100, "用户签名长度错误"),
    USER_SIGNATURE_INVALID(1102, "用户签名包含非法内容"),



    // 权限错误：3000 ~ 3499
    AUTH_FAIL(3000, "认证失败"),
    NO_OPERATE_AUTH(3001, "无权限操作"),
    NEED_ADMIN(3002, "需要管理员权限"),

    // 作品错误：3500 ~ 3599
    MATERIAL_REFERENCE_FAIL(3500, "素材引用失败"),
    ARTWORK_NOT_EXIST(3501, "作品不存在"),
    ARTWORK_SAVE_FAIL(3502, "作品保存失败"),
    ARTWORK_STATUS_ERROR(3503, "作品状态错误"),

    // 邮件错误：3600 ~ 3699
    MAIL_SEND_FAIL(3600, "邮件发送失败"),
    MAIL_SEND_TO_REQUIRE(3601, "收件人不能为空"),
    MAIL_SEND_SUBJECT_REQUIRE(3602, "邮件主题不能为空"),
    MAIL_SEND_CONTENT_REQUIRE(3603, "邮件内容不能为空"),
    MAIL_SEND_TO_INVALID(3604, "收件人格式不正确"),
    MAIL_SEND_FREQUENTLY(3605, "邮件发送过于频繁，请稍后再试"),
    ;

    final int code;
    final String message;

    HttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
