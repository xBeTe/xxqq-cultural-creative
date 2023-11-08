package com.xxz.model.common.dtos;

import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xzxie
 * @create 2023/7/4 16:21
 */
@Data
public class OkResponseResult<T> extends ResponseResult implements Serializable {

    private T data;
    private Map<String, Object> map = new HashMap<>();

    public OkResponseResult() {
    }

    public OkResponseResult(T data) {
        this.data = data;
    }

    public OkResponseResult<T> data(T data) {
        if (data != null) {
            this.setData(data);
        }
        return this;
    }

    public OkResponseResult<T> addMap(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public OkResponseResult<T> httpCodeEnum(HttpCodeEnum httpCodeEnum) {
        this.setCode(httpCodeEnum.getCode());
        this.setMessage(httpCodeEnum.getMessage());
        return this;
    }

    public static <T> OkResponseResult<T> result() {
        return  new OkResponseResult<T>().httpCodeEnum(HttpCodeEnum.SUCCESS);
    }

    public static <T> OkResponseResult<T> result(T data) {
        return new OkResponseResult<T>().data(data).httpCodeEnum(HttpCodeEnum.SUCCESS);
    }

    public static <T> OkResponseResult<T> result(HttpCodeEnum httpCodeEnum, T data) {
        return new OkResponseResult<T>().data(data).httpCodeEnum(httpCodeEnum);
    }

}
