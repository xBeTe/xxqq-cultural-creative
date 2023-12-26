package com.xxz.mall.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxz.mall.user.mapper.UserTypeMapper;
import com.xxz.mall.user.service.UserTypeService;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dos.MallUserTypeDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xzxie
 * @create 2023/12/19 20:34
 */
@Service
@Slf4j
@Transactional
public class UserTypeServiceImpl extends ServiceImpl<UserTypeMapper, MallUserTypeDO> implements UserTypeService {


    /**
     * 获取用户类型
     *
     * @return 返回用户类型
     */
    @Override
    public ResponseResult getUserType() {
        List<MallUserTypeDO> userTypeList = list(Wrappers.<MallUserTypeDO>lambdaQuery()
                .eq(MallUserTypeDO::getDeleted, false)
                .orderByAsc(MallUserTypeDO::getUpdateTime));
        return OkResponseResult.result(userTypeList);
    }
}
