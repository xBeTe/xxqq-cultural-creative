package com.xxz.common.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author xzxie
 * @create 2023/11/17 15:56
 */
@Data
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties implements Serializable {

    /**
     * token 加密密钥
     */
    private String encryptKey;

    /**
     * token 过期时间，单位秒
     */
    private Long expireTime;

    /**
     * token 过期后可刷新的时间，单位秒
     */
    private Long refreshTimeBeforeExpired;

}
