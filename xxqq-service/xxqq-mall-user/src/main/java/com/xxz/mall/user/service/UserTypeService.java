package com.xxz.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dos.MallUserTypeDO;

/**
 * @author xzxie
 * @create 2023/12/19 20:33
 */
public interface UserTypeService extends IService<MallUserTypeDO> {
    /**
     * 获取用户类型
     * @return 返回用户类型
     */
    ResponseResult getUserType();
}
