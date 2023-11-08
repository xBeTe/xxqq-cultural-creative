package com.xxz.model.common.enums;

import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/11/6 15:23
 */
public enum HttpCodeEnum  {

    // 成功
    SUCCESS(200, "操作成功"),

    // 登录段：1 ~ 50
    NEED_LOGIN(1,"需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2,"密码错误"),
    LOGIN_NAME_REQUIRE(3,"用户名/手机号/邮箱是必须的"),
    LOGIN_PASSWORD_REQUIRE(4,"密码是必须的"),
    LOGIN_USER_NOT_EXIST(5,"用户不存在"),
    REGISTER_EMAIL_REQUIRE(6,"邮箱是必须的"),
    REGISTER_PASSWORD_REQUIRE(7,"密码是必须的"),
    REGISTER_EMAIL_EXIST(8,"邮箱已经存在"),


    // TOKEN：50 ~ 100
    TOKEN_INVALID(50,"无效的 TOKEN"),
    TOKEN_EXPIRE(51,"TOKEN 已过期"),
    TOKEN_REQUIRE(52,"TOKEN 是必须的"),

    // 参数错误：500 ~ 1000
    PARAM_REQUIRE(500, "参数错误"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502,"图片格式有误"),
    PARAM_FILE_FORMAT_ERROR(503,"文件格式有误"),
    SERVER_ERROR(504,"服务器内部错误"),

    // 数据错误：1000 ~ 2000
    DATA_EXIST(1000,"数据已经存在"),
    DATA_NOT_EXIST(1001,"数据不存在"),
    DATA_SAVE_FAIL(1002,"数据保存失败"),
    USER_DATA_NOT_EXIST(1010,"User数据不存在"),
    ADMIN_DATA_NOT_EXIST(1011,"Admin数据不存在"),
    // 权限错误：3000 ~ 3500
    NO_OPERATOR_AUTH(3000,"无权限操作"),
    NEED_ADMIN(3001,"需要管理员权限"),

    // 作品错误：3501 ~ 3600
    MATERIAL_REFERENCE_FAIL(3501,"素材引用失败"),

    ARTWORK_NOT_EXIST(3502,"作品不存在"),
    ARTWORK_SAVE_FAIL(3503,"作品保存失败"),
    ARTWORK_STATUS_ERROR(3504,"作品状态错误");

    @Getter
    final int code;
    @Getter
    final String message;

    HttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
