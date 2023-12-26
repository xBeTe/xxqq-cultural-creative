package com.xxz.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxz.model.auth.dos.UserAccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xzxie
 * @create 2023/11/17 20:43
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountDO> {
}
