package com.xxz.model.mail.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzxie
 * @create 2023/11/10 20:22
 */
@Data
@Builder
public class ToMailDTO {

    /**
     * 收件人，可以多个
     */
    private List<String> tos;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    public String[] getTosArray() {
        return tos.toArray(new String[0]);
    }

    public void setSingleTo (String to) {
        if (this.tos == null) {
            this.tos = new ArrayList<>();
        }
        this.tos.add(to);
    }




}
