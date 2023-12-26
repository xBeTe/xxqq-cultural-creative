package com.xxz.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dos.MallUserInfoDO;
import com.xxz.model.user.dtos.UserInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xzxie
 * @create 2023/11/7 17:36
 */
public interface UserInfoService extends IService<MallUserInfoDO> {

    /**
     * 根据 token 获取用户信息
     *
     * @param token token
     * @return 返回用户信息
     */
    ResponseResult getUserInfo(String token);

    /**
     * 更新用户信息
     *
     * @param userInfoDTO 用户信息
     * @param file        头像文件
     * @return 返回更新结果
     */
    ResponseResult updateUserInfo(UserInfoDTO userInfoDTO, MultipartFile file);
}
