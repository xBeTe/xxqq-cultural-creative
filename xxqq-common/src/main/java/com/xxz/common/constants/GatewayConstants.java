package com.xxz.common.constants;

/**
 * @author xzxie
 * @create 2023/11/14 22:16
 */
public class GatewayConstants {

    public static final String HTTP_HEADER_USER_ID = "userId";
    public static final String HTTP_HEADER_TOKEN = "token";
    public static final String HTTP_HEADER_TOKEN_SUBJECT = "token-subject";
    public static final String HTTP_HEADER_USERNAME = "username";

    public static final String[] EXCLUDED_AUTH_PATHS = {
            "/auth/login",
            "/artwork/classify",
            "/*/doc.html",
            "/*/v2/api-docs-ext",
            "/*/swagger-resources",
            "/*/v2/api-docs",
            "/*/swagger-ui.html",
            "/*/swagger-resources/configuration/ui",
            "/*/swagger-resources/configuration/security"
    };
}
