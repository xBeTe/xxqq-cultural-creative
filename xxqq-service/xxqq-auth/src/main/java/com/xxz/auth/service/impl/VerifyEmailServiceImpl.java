package com.xxz.auth.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxz.apis.mail.IMailClient;
import com.xxz.auth.mapper.UserAccountMapper;
import com.xxz.auth.service.VerifyEmailService;
import com.xxz.common.cache.RedisUtils;
import com.xxz.common.constants.AuthConstants;
import com.xxz.common.constants.MailConstants;
import com.xxz.common.jwt.JwtUtil;
import com.xxz.model.auth.dos.UserAccountDO;
import com.xxz.model.auth.enums.AccountStatus;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import com.xxz.model.mail.dtos.CodeCacheDTO;
import com.xxz.model.auth.dtos.MailVerificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xzxie
 * @create 2023/11/7 17:37
 */
@Service
@Transactional
@Slf4j
public class VerifyEmailServiceImpl implements VerifyEmailService {

    @Autowired
    private UserAccountMapper userAccountMapper;


    @Autowired
    private IMailClient mailClient;

    /**
     * 发送验证码
     *
     * @param email 邮箱地址
     * @return 返回发送结果
     */
    @Override
    public ResponseResult sendCode(String email) {
        // 校验参数
        if (StringUtils.isBlank(email)) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_EMAIL_REQUIRE);
        }

        // 检查数据库中是否存在当前邮箱
        UserAccountDO userAccount = userAccountMapper.selectOne(Wrappers.<UserAccountDO>lambdaQuery().eq(UserAccountDO::getEmail, email));
        if (userAccount != null) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_EMAIL_EXIST);
        }

        if (!Validator.isEmail(email)) {
            return ErrorResponseResult.result(HttpCodeEnum.MAIL_SEND_TO_INVALID);
        }

        // 检查 Redis 缓存中是否存在
        String key = MailConstants.MAIL_VERIFICATION_CACHE_PREFIX + email;
        CodeCacheDTO cacheDto = JSON.parseObject(redisUtils.get(key), CodeCacheDTO.class);
        // 校验是否重复发送
        String code = null;
        if (cacheDto != null) {

            if (cacheDto.getGenerateTime() + MailConstants.MAIL_VERIFICATION_INTERVAL > System.currentTimeMillis()) {
                return ErrorResponseResult.result(HttpCodeEnum.MAIL_SEND_FREQUENTLY);
            } else {
                code = cacheDto.getCode();
            }
        } else {
            // 验证码失效，生成验证码
            code = RandomUtil.randomNumbers(4);
            log.info("email = {}, code = {}", email, code);
            cacheDto = CodeCacheDTO
                    .builder()
                    .code(code).generateTime(System.currentTimeMillis()).build();

            // 将验证码消息缓存到 Redis 中
            redisUtils.set(key, JSON.toJSONString(cacheDto), MailConstants.MAIL_VERIFICATION_EXPIRE);
        }

        return mailClient.sendCode(MailVerificationDTO
                .builder()
                .email(email)
                .code(code)
                .build());

        // return OkResponseResult.result();
    }

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 验证邮箱
     *
     * @param dto 验证信息
     * @return 返回验证结果
     */
    @Override
    public ResponseResult verify(MailVerificationDTO dto) {
        // 校验参数
        if (dto == null) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_REQUIRE);
        } else if (StringUtils.isBlank(dto.getEmail())) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_EMAIL_REQUIRE);
        } else if (StringUtils.isBlank(dto.getCode())) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_CODE_REQUIRE);
        }

        // 校验验证码
        String key = MailConstants.MAIL_VERIFICATION_CACHE_PREFIX + dto.getEmail();
        CodeCacheDTO cacheDTO = JSON.parseObject(redisUtils.get(key), CodeCacheDTO.class);
        if (cacheDTO == null) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_CODE_EXPIRE);
        }

        if (!dto.getCode().equals(cacheDTO.getCode())) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_CODE_ERROR);
        }

        // 验证通过，删除缓存
        if (redisUtils.delete(key)) {
            log.info("delete cache success, key: {}", key);
        } else {
            log.info("delete cache fail, key: {}", key);
        }

        // 使用邮箱对用户进行预注册
        UserAccountDO preUserAccount = UserAccountDO.builder()
                .email(dto.getEmail())
                .status(AccountStatus.INACTIVE.getCode())
                .build();
        userAccountMapper.insert(preUserAccount);

        // 生成邮箱验证 token
        String token = jwtUtil.generateEmailVerifiedToken(preUserAccount.getId(), preUserAccount.getEmail());

        return OkResponseResult.result().addMap(AuthConstants.TOKEN_RESPONSE_KEY, token);
    }


}
