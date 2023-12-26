package com.xxz.apis.mall.user;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xzxie
 * @create 2023/11/15 10:33
 */
@FeignClient(value = "xxqq-mall-user")
public interface MallUserClient {
}
