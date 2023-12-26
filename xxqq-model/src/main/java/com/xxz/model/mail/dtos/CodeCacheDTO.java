package com.xxz.model.mail.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * @author xzxie
 * @create 2023/11/12 11:34
 */
@Data
@Builder
public class CodeCacheDTO {

    /**
     * 验证码
     */
    private String code;

    /**
     * 生成时间
     */
    private Long generateTime;
}
