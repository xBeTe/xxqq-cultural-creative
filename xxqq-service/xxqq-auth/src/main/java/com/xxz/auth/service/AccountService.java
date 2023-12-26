package com.xxz.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxz.model.auth.dos.UserAccountDO;
import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.auth.dtos.RegisterDTO;
import com.xxz.model.common.dtos.ResponseResult;

/**
 * @author xzxie
 * @create 2023/11/24 11:25
 */
public interface AccountService extends IService<UserAccountDO> {
    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @return 返回检查结果
     */
    ResponseResult checkUsername(String username);

    /**
     * 用户注册
     * @param userId 用户id
     * @param dto 注册信息
     * @return 返回注册结果
     */
    ResponseResult register(String userId, RegisterDTO dto);

    /**
     * 设置密码
     * @param userId 用户id
     * @param dto 密码
     * @return 返回设置结果
     */
    ResponseResult updatePassword(String userId, LoginUserDTO dto);
}
