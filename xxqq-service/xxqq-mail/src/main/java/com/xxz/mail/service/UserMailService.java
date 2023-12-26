package com.xxz.mail.service;

import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.auth.dtos.MailVerificationDTO;

/**
 * @author xzxie
 * @create 2023/11/10 17:01
 */
public interface UserMailService {
    /**
     * 发送验证码
     * @param dto 邮箱地址和验证码
     * @return 返回发送结果
     */
    ResponseResult sendCode(MailVerificationDTO dto);


}
