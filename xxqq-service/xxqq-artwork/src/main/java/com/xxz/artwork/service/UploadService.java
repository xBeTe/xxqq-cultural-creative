package com.xxz.artwork.service;

import com.xxz.model.artwork.dtos.ArtworkUploadDTO;
import com.xxz.model.common.dtos.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xzxie
 * @create 2023/12/16 22:47
 */
public interface UploadService {

    /**
     * 上传素材
     * @param userId 用户id
     * @param username 用户名
     * @param multipartFile 素材文件
     * @return 响应结果
     */
    ResponseResult uploadMaterial(String userId, String username, ArtworkUploadDTO uploadDTO, MultipartFile multipartFile);
}
