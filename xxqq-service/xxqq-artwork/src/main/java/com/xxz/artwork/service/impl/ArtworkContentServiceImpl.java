package com.xxz.artwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxz.artwork.mapper.ArtworkContentMapper;
import com.xxz.artwork.service.ArtworkContentService;
import com.xxz.model.artwork.dos.ArtworkContentDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xzxie
 * @create 2023/12/17 22:05
 */
@Service
@Slf4j
public class ArtworkContentServiceImpl extends ServiceImpl<ArtworkContentMapper, ArtworkContentDO> implements ArtworkContentService {
}
