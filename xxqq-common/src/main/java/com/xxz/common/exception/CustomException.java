package com.xxz.common.exception;

import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/11/6 20:40
 */
@Getter
public class CustomException extends RuntimeException {

    private HttpCodeEnum httpCodeEnum;

    public CustomException(HttpCodeEnum httpCodeEnum) {
        this.httpCodeEnum = httpCodeEnum;
    }


}
