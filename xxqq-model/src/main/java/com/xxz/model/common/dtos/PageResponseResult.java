package com.xxz.model.common.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xzxie
 * @create 2023/11/6 19:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponseResult<T> extends OkResponseResult<T> implements Serializable {
    private Integer currentPage;
    private Integer size;
    private Integer total;

    public PageResponseResult() {
    }

    public PageResponseResult(Integer currentPage, Integer size, Integer total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }

    public PageResponseResult<T> currentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public PageResponseResult<T> size(Integer size) {
        this.size = size;
        return this;
    }

    public PageResponseResult<T> total(Integer total) {
        this.total = total;
        return this;
    }

    public static <T> PageResponseResult<T> result() {
        return new PageResponseResult<T>();
    }

    public static <T> PageResponseResult<T> result(T data) {
        PageResponseResult<T> result = result();
        result.setData(data);
        return result;
    }

    public static <T> PageResponseResult<T> result(int currentPage, int size, int total) {
        PageResponseResult<T> result = result();
        return result.currentPage(currentPage)
                .size(size)
                .total(total);
    }

    public static <T> PageResponseResult<T> result(T data, int currentPage, int size, int total) {
        PageResponseResult<T> result = result();
        result.setData(data);
        result.currentPage(currentPage)
                .size(size)
                .total(total);
        return result;
    }




}
