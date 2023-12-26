package com.xxz.mail.service.impl;

import com.xxz.mail.service.SendService;
import com.xxz.mail.service.UserMailService;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import com.xxz.model.mail.dtos.ToMailDTO;
import com.xxz.model.mail.vos.MailVerificationVO;
import com.xxz.model.auth.dtos.MailVerificationDTO;
import com.xxz.common.constants.MailConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author xzxie
 * @create 2023/11/10 17:01
 */
@Slf4j
@Service
public class UserMailServiceImpl implements UserMailService {

    @Autowired
    private SendService sendService;

    @Autowired
    private TemplateEngine templateEngine;
    /**
     * 发送验证码
     * @param dto 邮箱地址和验证码
     * @return 返回发送结果
     */
    public ResponseResult sendCode(MailVerificationDTO dto) {
        // 校验参数
        if (dto == null || StringUtils.isBlank(dto.getCode())) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_REQUIRE);
        } else if (StringUtils.isBlank(dto.getEmail())) {
            return ErrorResponseResult.result(HttpCodeEnum.MAIL_SEND_TO_REQUIRE);
        }

        // 封装邮箱验证视图对象
        MailVerificationVO verificationVo = MailVerificationVO.builder()
                .code(dto.getCode())
                .message(MailConstants.MAIL_VERIFICATION_MESSAGE)
                .validityPeriod((int) (MailConstants.MAIL_VERIFICATION_EXPIRE / 60))
                .serviceUrl(MailConstants.MAIL_VERIFICATION_SERVICE_URL)
                .build();
        // 渲染模板
        Context context = new Context();
        context.setVariable("mailVerification", verificationVo);
        String content = templateEngine.process("mail-verification-code.html", context);

        // 封装邮件信息
        ToMailDTO toMail = ToMailDTO.builder()
                .subject(MailConstants.MAIL_VERIFICATION_SUBJECT)
                .content(content)
                .build();
        toMail.setSingleTo(dto.getEmail());

        // 发送验证码
        return sendService.sendHtmlMail(toMail);
    }
}
