package com.xxz.auth.exception;

import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xzxie
 * @create 2023/11/22 16:23
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class SecurityExceptionCatch {

    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseResult exception(BadCredentialsException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.LOGIN_FAIL);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseResult exception(UsernameNotFoundException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.USER_DATA_NOT_EXIST);
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseResult exception(AccountExpiredException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.USER_ACCOUNT_EXPIRED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseResult exception(LockedException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.USER_ACCOUNT_LOCKED);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseResult exception(CredentialsExpiredException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.TOKEN_EXPIRED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseResult exception(DisabledException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.USER_ACCOUNT_DISABLE);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseResult exception(AuthenticationServiceException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
    }




}
