package com.xxz.common.exception;


import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  // 控制器增强类
@Slf4j
public class ExceptionCatch {

    /**
     * 处理不可控异常
     *
     * @param e 异常
     * @return 返回错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        log.error("catch exception: ", e);
        return ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 处理可控异常  自定义异常
     *
     * @param e 自定义异常
     * @return 返回自定义异常的错误信息
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult exception(CustomException e) {
        log.error("catch exception:{}", e.getMessage());
        return ErrorResponseResult.result(e.getHttpCodeEnum());
    }
}
