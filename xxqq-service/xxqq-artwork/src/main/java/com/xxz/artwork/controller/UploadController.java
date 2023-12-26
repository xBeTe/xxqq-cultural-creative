package com.xxz.artwork.controller;

import com.xxz.artwork.service.UploadService;
import com.xxz.common.constants.ArtworkConstant;
import com.xxz.common.constants.GatewayConstants;
import com.xxz.model.artwork.dtos.ArtworkUploadDTO;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xzxie
 * @create 2023/12/16 22:43
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@Api(tags = "上传接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/material")
    @ApiOperation("上传素材")
    public ResponseResult uploadMaterial(HttpServletRequest request,
                                         ArtworkUploadDTO uploadDTO,
                                         @RequestParam(ArtworkConstant.FILE_FORM_DATA_KEY) MultipartFile multipartFile) {
        String userId = request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID);
        String username = request.getHeader(GatewayConstants.HTTP_HEADER_USERNAME);
        return uploadService.uploadMaterial(userId, username, uploadDTO, multipartFile);
    }
}
