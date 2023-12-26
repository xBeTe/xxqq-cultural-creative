package com.xxz.common.jwt;

import com.xxz.common.constants.JwtConstants;
import com.xxz.common.exception.CustomException;
import com.xxz.model.common.enums.HttpCodeEnum;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author xzxie
 * @create 2023/7/6 17:44
 */
@Slf4j
@Getter
@EnableConfigurationProperties(JwtProperties.class)
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 生成token
     */
    public String generateToken(Long id, String username, String subject, String issuer, String audience, Long expireTime) {

        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime))  // 签发时间
                .setSubject(subject)  // 说明
                .setIssuer(issuer) // 签发者信息
                .setAudience(audience)  // 接收用户
                .compressWith(CompressionCodecs.GZIP)  // 数据压缩方式
                .signWith(SignatureAlgorithm.HS512, generalKey(jwtProperties.getEncryptKey())) // 加密方式
                .setExpiration(new Date(currentTime + expireTime * 1000L))  // 过期时间戳
                .addClaims(createClaims(id, username)) // cla信息
                .compact();
    }

    public String generateUserAuthToken(Long id, String username) {
        return generateToken(id, username, JwtConstants.TOKEN_SUBJECT_USER_AUTH, JwtConstants.TOKEN_ISSUER, JwtConstants.TOKEN_AUDIENCE_MALL, jwtProperties.getExpireTime());
    }

    public String generateAdminAuthToken(Long id, String username) {
        return generateToken(id, username, JwtConstants.TOKEN_SUBJECT_ADMIN_AUTH, JwtConstants.TOKEN_ISSUER, JwtConstants.TOKEN_AUDIENCE_ADMIN, jwtProperties.getExpireTime());
    }

    public String generateEmailVerifiedToken(Long id, String username) {
        return generateToken(id, username, JwtConstants.TOKEN_SUBJECT_EMAIL_VERIFIED, JwtConstants.TOKEN_ISSUER, JwtConstants.TOKEN_AUDIENCE_MALL, JwtConstants.EMAIL_TOKEN_EXPIRATION);
    }

    private Map<String, Object> createClaims(Long id, String username) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put(JwtConstants.CLAIM_KEY_USER_ID, id);
        claimMap.put(JwtConstants.CLAIM_KEY_USERNAME, username);
        return claimMap;
    }

    /**
     * 获取payload body信息
     *
     * @param token 令牌
     * @return Claims
     */
    public Claims getClaimsBody(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey(jwtProperties.getEncryptKey()))
                    .parseClaimsJws(token)
                    .getBody();
            return claims;

        } catch (ExpiredJwtException e) {
            // 过期
            log.error("token expired:{}", e.getMessage());
            throw new CustomException(HttpCodeEnum.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            // 签名失败
            log.error("token signature error:{}", e.getMessage());
            throw new CustomException(HttpCodeEnum.TOKEN_SIGNATURE_ERROR);
        } catch (Exception e) {
            log.error("token error:{}", e.getMessage());
            throw new CustomException(HttpCodeEnum.TOKEN_INVALID);
        }
    }

    /**
     * 验证 token 是否有效
     *
     * @param token token
     * @return res -1：有效且需要刷新，0：有效，1：过期，2：无效
     */
    public int validateToken(String token) {
        try {
            Claims claims = getClaimsBody(token);
            if (claims == null) {
                return 2;
            }

            // 判断当前时间是否满足刷新阈值
            if (!Objects.equals(claims.getSubject(), JwtConstants.TOKEN_SUBJECT_EMAIL_VERIFIED)) {
                if (claims.getExpiration().getTime() < System.currentTimeMillis() + jwtProperties.getRefreshTimeBeforeExpired() * 1000L) {
                    return -1;
                }
            }
            return 0;
        } catch (CustomException e) {
            log.error("validate token error:{}", e.getMessage());
            if (e.getHttpCodeEnum() == HttpCodeEnum.TOKEN_EXPIRED) {
                return 1;
            } else {
                return 2;
            }
        } catch (Exception e) {
            log.error("validate token error:{}", e.getMessage());
            return 2;
        }
    }

    /**
     * 刷新 token
     * @param token token
     * @return newToken
     */
    public String refreshToken(String token) {
        String newToken = null;
        Claims claimsBody = getClaimsBody(token);
        if (claimsBody != null) {
            newToken = generateToken(claimsBody.get(JwtConstants.CLAIM_KEY_USER_ID, Long.class), claimsBody.get(JwtConstants.CLAIM_KEY_USERNAME, String.class), claimsBody.getSubject(), claimsBody.getIssuer(), claimsBody.getAudience(), jwtProperties.getExpireTime());
        }
        return newToken;
    }

    /**
     * 由字符串生成加密key
     *
     * @param key key
     * @return SecretKey
     */
    private SecretKey generalKey(String key) {
        byte[] encodedKey = Base64.getEncoder().encode(key.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

}
