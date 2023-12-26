package com.xxz.auth.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxz.auth.mapper.UserAccountMapper;
import com.xxz.auth.service.AccountService;
import com.xxz.model.auth.dos.UserAccountDO;
import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.auth.dtos.RegisterDTO;
import com.xxz.model.auth.enums.AccountStatus;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xzxie
 * @create 2023/11/24 11:25
 */
@Service
@Slf4j
@Transactional
public class AccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccountDO> implements AccountService {


    /**
     * 检查用户名是否可用
     *
     * @param username 用户名
     * @return 返回检查结果
     */
    @Override
    public ResponseResult checkUsername(String username) {
        // 校验参数
        if (StringUtils.isBlank(username)) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_INVALID);
        }

        log.info("check username: {}", username);

        // 检查 username 长度
        if (StringUtils.length(username) < 3 || StringUtils.length(username) > 12) {
            return ErrorResponseResult.result(HttpCodeEnum.USERNAME_INVALID);
        }

        // TODO 检查 username 是否合法（内容审核、敏感词过滤）

        // 检查 username 是否存在
        int count = count(Wrappers.<UserAccountDO>lambdaQuery()
                .eq(UserAccountDO::getUsername, username));
        if (count > 0) {
            return ErrorResponseResult.result(HttpCodeEnum.USERNAME_EXIST);
        }

        return OkResponseResult.result();
    }

    /**
     * 注册
     *
     * @param registerDto 注册信息
     * @return 返回注册成功的用户信息
     */
    @Override
    public ResponseResult register(String userId, RegisterDTO registerDto) {

        // 校验参数
        if (StringUtils.isBlank(userId)) {
            return ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
        }

        // 更新用户信息
        UserAccountDO userAccountDO = UserAccountDO.builder().id(Long.parseLong(userId)).build();
        BeanUtils.copyProperties(registerDto, userAccountDO);

        // 设置账号状态为正常
        userAccountDO.setStatus(AccountStatus.NORMAL.getCode());

        boolean update = updateById(userAccountDO);

        // 保存用户信息
        return update ? OkResponseResult.result()
                : ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 设置密码
     *
     * @param userId   用户id
     * @param dto 密码
     * @return 返回设置结果
     */
    @Override
    public ResponseResult updatePassword(String userId, LoginUserDTO dto) {
        // 校验参数
        if (StringUtils.isBlank(userId) || dto == null || StringUtils.isBlank(dto.getPassword())) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_INVALID);
        }
        String password = dto.getPassword();
        log.info("user {} update password: {}",userId,  password);

        // 校验密码长度
        boolean general = Validator.isGeneral(password, 6, 16);
        if (!general) {
            return ErrorResponseResult.result(HttpCodeEnum.PASSWORD_INVALID);
        }

        // 加密密码
        password = passwordEncoder.encode(password);
        // 更新用户密码
        UserAccountDO userAccountDO = UserAccountDO.builder().id(Long.parseLong(userId)).password(password).build();
        boolean update = updateById(userAccountDO);

        return update ? OkResponseResult.result()
                : ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
    }


}
