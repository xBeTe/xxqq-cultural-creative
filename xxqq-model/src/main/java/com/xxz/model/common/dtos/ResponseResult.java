package com.xxz.model.common.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xzxie
 * @create 2023/7/4 16:21
 */
@Data
public class ResponseResult implements Serializable {

    private Integer code;

    private String message;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }




}
