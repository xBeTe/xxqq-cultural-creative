package com.xxz.model.common.dtos;

import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xzxie
 * @create 2023/11/6 16:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseResult extends ResponseResult implements Serializable {

    public ErrorResponseResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ErrorResponseResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public ErrorResponseResult httpCodeEnum(HttpCodeEnum httpCodeEnum) {
        this.setCode(httpCodeEnum.getCode());
        this.setMessage(httpCodeEnum.getMessage());
        return this;
    }

    public static ErrorResponseResult result(HttpCodeEnum httpCodeEnum) {
        return new ErrorResponseResult().httpCodeEnum(httpCodeEnum);
    }

    public static ErrorResponseResult result(Integer code, String message) {
        return new ErrorResponseResult().code(code).message(message);
    }

    public static ErrorResponseResult result(HttpCodeEnum httpCodeEnum, String message) {
        return result(httpCodeEnum.getCode(), message);
    }
}
