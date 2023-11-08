package com.xxz.model.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xzxie
 * @create 2023/11/6 22:13
 */
@Data
public class JwtProperty implements Serializable {

    private Long expireTime;
    // 加密KEY
    private String tokenEncryptKey;
    // 最小刷新间隔(S)
    private Integer refreshTime;
    // 签发主题
    private String subject;
    // 签发者
    private String issuer;
    // 接收用户
    private String audience;
}
