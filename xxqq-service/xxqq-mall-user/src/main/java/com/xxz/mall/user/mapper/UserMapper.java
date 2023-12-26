package com.xxz.mall.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxz.model.user.dos.MallUserInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xzxie
 * @create 2023/11/7 17:35
 */
@Mapper
public interface UserMapper extends BaseMapper<MallUserInfoDO> {

}
