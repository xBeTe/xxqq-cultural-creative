package com.xxz.artwork.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxz.artwork.mapper.ArtworkClassifyMapper;
import com.xxz.artwork.service.ArtworkClassifyService;
import com.xxz.model.artwork.dos.ArtworkClassifyDO;
import com.xxz.model.auth.enums.SimpleStatus;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xzxie
 * @create 2023/12/17 21:33
 */
@Service
@Slf4j
public class ArtworkClassifyServiceImpl extends ServiceImpl<ArtworkClassifyMapper, ArtworkClassifyDO> implements ArtworkClassifyService {
    /**
     * 获取作品分类
     * @return 返回作品分类
     */
    @Override
    public ResponseResult getArtworkClassify() {

        List<ArtworkClassifyDO> artworkClassifyList = list(Wrappers.<ArtworkClassifyDO>lambdaQuery()
                .eq(ArtworkClassifyDO::getStatus, SimpleStatus.NORMAL.getCode())
                .eq(ArtworkClassifyDO::getDeleted, false)
                .orderByAsc(ArtworkClassifyDO::getOrderNum)
                .orderByDesc(ArtworkClassifyDO::getCreateTime));
        return OkResponseResult.result(artworkClassifyList);
    }
}
