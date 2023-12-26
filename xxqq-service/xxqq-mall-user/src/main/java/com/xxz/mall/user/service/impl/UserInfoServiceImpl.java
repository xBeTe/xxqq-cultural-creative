package com.xxz.mall.user.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxz.common.constants.MinioConstants;
import com.xxz.common.constants.UserConstant;
import com.xxz.file.service.FileStorageService;
import com.xxz.mall.user.mapper.UserMapper;
import com.xxz.mall.user.service.UserInfoService;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import com.xxz.model.user.dos.MallUserInfoDO;
import com.xxz.model.user.dos.MallUserTypeDO;
import com.xxz.model.user.dtos.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author xzxie
 * @create 2023/11/7 17:37
 */
@Service
@Transactional
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserMapper, MallUserInfoDO> implements UserInfoService {


    /**
     * 根据 token 获取用户信息
     *
     * @param userId 用户 id
     * @return 返回用户信息
     */
    @Override
    public ResponseResult getUserInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_REQUIRE);
        }

        log.info("UserInfoServiceImpl-getUserInfo, userId = {}", userId);

        MallUserInfoDO user = getById(userId);
        if (user == null) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_USER_NOT_EXIST);
        }
        return OkResponseResult.<MallUserInfoDO>result(user);
    }

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 更新用户信息
     *
     * @param userInfoDTO 用户信息
     * @param file        头像文件
     * @return 返回更新结果
     */
    @Override
    public ResponseResult updateUserInfo(UserInfoDTO userInfoDTO, MultipartFile file) {
        // 检查参数
        if (userInfoDTO == null || userInfoDTO.getId() == null) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_REQUIRE);
        }

        boolean general = Validator.isGeneral(userInfoDTO.getNickname(), UserConstant.MALL_USER_NICKNAME_MIN_LEN, UserConstant.MALL_USER_NICKNAME_MAX_LEN);
        if (!general) {
            return ErrorResponseResult.result(HttpCodeEnum.USER_NICKNAME_LEN_INVALID);
        }

        if (file != null) {
            String avatarUrl = uploadAvatar(file);
            if (StringUtils.isNotBlank(avatarUrl)) {
                userInfoDTO.setAvatar(avatarUrl);
            }
        }

        MallUserInfoDO user = new MallUserInfoDO();
        BeanUtils.copyProperties(userInfoDTO, user);
        boolean update = updateById(user);
        return update ? OkResponseResult.result()
                : ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR);
    }

    public String uploadAvatar(MultipartFile multipartFile) {
        String avatarUrl = null;
        try {
            avatarUrl = fileStorageService.uploadImgFile(MinioConstants.ARTWORK_CONTENT_PREFIX, getRandomFileName(Objects.requireNonNull(multipartFile.getOriginalFilename())), multipartFile.getInputStream());
            log.info("upload file to minio success! fileUrl = {}", avatarUrl);
        } catch (IOException e) {
            log.error("Failed to upload file to minio, reason: {}", e.getMessage());
        }

        return avatarUrl;
    }

    public String getRandomFileName(String originalFilename) {
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String postfix = originalFilename.substring(originalFilename.indexOf("."));
        return fileName + postfix;
    }
}
