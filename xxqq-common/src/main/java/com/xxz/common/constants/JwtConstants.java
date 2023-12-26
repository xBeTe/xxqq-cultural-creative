package com.xxz.common.constants;

/**
 * @author xzxie
 * @create 2023/11/17 10:28
 */
public class JwtConstants {

    public static final String CLAIM_KEY_USER_ID = "id";
    public static final String CLAIM_KEY_USERNAME = "username";

    public static final Long EMAIL_TOKEN_EXPIRATION = 10 * 60 * 1000L;

    public static final String TOKEN_SUBJECT_USER_AUTH = "userAuth";
    public static final String TOKEN_SUBJECT_ADMIN_AUTH = "adminAuth";
    public static final String TOKEN_SUBJECT_EMAIL_VERIFIED = "emailVerified";

    public static final String TOKEN_ISSUER = "xxz";
    public static final String TOKEN_AUDIENCE_MALL = "xxqqMall";
    public static final String TOKEN_AUDIENCE_ADMIN = "xxqqAdmin";
}
