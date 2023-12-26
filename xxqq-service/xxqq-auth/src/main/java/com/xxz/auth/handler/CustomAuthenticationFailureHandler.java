package com.xxz.auth.handler;

import com.xxz.common.util.WebUtils;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xzxie
 * @create 2023/11/21 21:54
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        ErrorResponseResult responseResult = null;
        if (exception instanceof BadCredentialsException) {
            responseResult = ErrorResponseResult.result(HttpCodeEnum.LOGIN_FAIL);
        }

        WebUtils.renderJSONString(response, responseResult);
    }
}
