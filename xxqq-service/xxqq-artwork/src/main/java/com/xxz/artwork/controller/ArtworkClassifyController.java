package com.xxz.artwork.controller;

import com.xxz.artwork.service.ArtworkClassifyService;
import com.xxz.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzxie
 * @create 2023/12/17 21:25
 */
@RestController
@Api(tags = "作品分类接口")
public class ArtworkClassifyController {

    @Autowired
    private ArtworkClassifyService artworkClassifyService;

    @GetMapping("/classify")
    @ApiOperation("获取作品分类")
    public ResponseResult getArtworkClassify() {
        return artworkClassifyService.getArtworkClassify();
    }
}
