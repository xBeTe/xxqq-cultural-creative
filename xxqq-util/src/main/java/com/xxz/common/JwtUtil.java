package com.xxz.common;

import com.xxz.model.config.JwtProperty;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author xzxie
 * @create 2023/7/6 17:44
 */
@Slf4j
public class JwtUtil {

    // // TOKEN的有效期一天（S）
    // private static final Long EXPIRE_TIME = 24 * 60 * 60L;
    // // 加密KEY
    // private static final String TOKEN_ENCRYPT_KEY = "840Ebc9E2Eef0A00435A9De39Fa85Ee85Ff9F11C6Aaef3D773B6158B9Cbc7Ac9";
    // // 最小刷新间隔(S)
    // private static final int REFRESH_TIME = 300;

    public static JwtProperty jwtConfig;
    public static final String CLAIM_KEY_USER_ID = "id";
    public static final String CLAIM_KEY_USERNAME = "username";

    // 生产ID
    public static String getToken(Long id, String username) {

        long currentTime = System.currentTimeMillis();

        String subject = jwtConfig.getSubject();
        String issuer = jwtConfig.getIssuer();
        String audience = jwtConfig.getAudience();
        String tokenEncryptKey = jwtConfig.getTokenEncryptKey();
        Long expireTime = jwtConfig.getExpireTime();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime))  // 签发时间
                .setSubject(subject)  // 说明
                .setIssuer(issuer) // 签发者信息
                .setAudience(audience)  // 接收用户
                .compressWith(CompressionCodecs.GZIP)  // 数据压缩方式
                .signWith(SignatureAlgorithm.HS512, generalKey(tokenEncryptKey)) // 加密方式
                .setExpiration(new Date(currentTime + expireTime * 1000))  // 过期时间戳
                .addClaims(createClaims(id, username)) // cla信息
                .compact();
    }

    private static Map<String, Object> createClaims(Long id, String username) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put(CLAIM_KEY_USER_ID, id);
        claimMap.put(CLAIM_KEY_USERNAME, username);
        return claimMap;
    }

    /**
     * 获取token中的claims信息
     *
     * @param token 令牌
     * @return Jws<Claims>
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey(jwtConfig.getTokenEncryptKey()))
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token 令牌
     * @return Claims
     */
    public static Claims getClaimsBody(String token) {
        Claims claims = null;
        try {
            claims = getJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 过期
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 是否过期
     *
     * @param token token
     * @return -1：有效且需要刷新，0：有效，1：过期，2：无效
     */
    public static int validateToken(String token) {
        Claims claims = getClaimsBody(token);

        int valid = 0;
        if (claims == null) {
            return 2;
        }

        try {
            Date expiration = claims.getExpiration();
            boolean before = expiration.before(new Date());
            // 需要自动刷新TOKEN
            if ((expiration.getTime() - System.currentTimeMillis()) > jwtConfig.getRefreshTime() * 1000) {
                valid = -1;
            } else {
                valid = before ? 1 : 0;
            }
        } catch (ExpiredJwtException e) {
            valid = 1;
        } catch (Exception e) {
            valid = 2;
        }
        return valid;
    }

    /**
     * 由字符串生成加密key
     *
     * @param key key
     * @return SecretKey
     */
    public static SecretKey generalKey(String key) {
        byte[] encodedKey = Base64.getEncoder().encode(key.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

}
