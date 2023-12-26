package com.xxz.artwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxz.model.artwork.dos.ArtworkClassifyDO;
import com.xxz.model.common.dtos.ResponseResult;

/**
 * @author xzxie
 * @create 2023/12/17 21:27
 */
public interface ArtworkClassifyService extends IService<ArtworkClassifyDO> {
    /**
     * 获取作品分类
     * @return 返回作品分类
     */
    ResponseResult getArtworkClassify();
}
