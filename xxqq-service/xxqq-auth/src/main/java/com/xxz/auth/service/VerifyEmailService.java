package com.xxz.auth.service;

import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.auth.dtos.MailVerificationDTO;

/**
 * @author xzxie
 * @create 2023/11/7 17:36
 */
public interface VerifyEmailService {

    /**
     * 发送验证码
     * @param email 邮箱地址
     * @return 返回发送结果
     */
    ResponseResult sendCode(String email);


    /**
     * 验证邮箱
     * @param dto 验证信息
     * @return 返回验证结果
     */
    ResponseResult verify(MailVerificationDTO dto);

}
