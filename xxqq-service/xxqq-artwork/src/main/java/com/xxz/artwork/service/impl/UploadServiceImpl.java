package com.xxz.artwork.service.impl;

import com.xxz.artwork.mapper.ArtworkBaseMapper;
import com.xxz.artwork.mapper.ArtworkContentMapper;
import com.xxz.artwork.service.ArtworkBaseService;
import com.xxz.artwork.service.ArtworkContentService;
import com.xxz.artwork.service.UploadService;
import com.xxz.common.constants.ArtworkConstant;
import com.xxz.common.constants.MinioConstants;
import com.xxz.file.service.FileStorageService;
import com.xxz.model.artwork.dos.ArtworkBaseDO;
import com.xxz.model.artwork.dos.ArtworkContentDO;
import com.xxz.model.artwork.dtos.ArtworkUploadDTO;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author xzxie
 * @create 2023/12/16 22:48
 */
@Slf4j
@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ArtworkBaseService artworkBaseService;

    @Autowired
    private ArtworkContentService artworkContentService;

    /**
     * 上传素材
     *
     * @param userId        用户id
     * @param username      用户名
     * @param multipartFile 素材文件
     * @return 响应结果
     */
    @Override
    public ResponseResult uploadMaterial(String userId, String username, ArtworkUploadDTO uploadDTO, MultipartFile multipartFile) {

        // 检查参数
        if (multipartFile == null || multipartFile.isEmpty()) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_INVALID);
        }

        if (uploadDTO == null
                || uploadDTO.getName() == null || uploadDTO.getName().length() > 20
                || uploadDTO.getDescription() == null || uploadDTO.getDescription().length() > 140
                || uploadDTO.getClassifyId() == null
        ) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_INVALID);
        }
        ArtworkBaseDO artworkBase = null;

        // 上传原始文件到 minio
        String fileUrl = uploadContent(multipartFile);

        // 上传封面到 minio
        String coverUrl = uploadCover(multipartFile);

        try {
            // 保存封面数据到数据库
            artworkBase = ArtworkBaseDO.builder()
                    .cover(coverUrl)
                    .userId(Long.valueOf(userId))
                    .author(username)
                    .publishTime(LocalDateTime.now())
                    .build();
            BeanUtils.copyProperties(uploadDTO, artworkBase);
            artworkBaseService.save(artworkBase);

            // 保存内容数据到数据库
            ArtworkContentDO artworkContent = ArtworkContentDO.builder().content(fileUrl).artworkId(artworkBase.getId()).build();
            artworkContentService.save(artworkContent);
        } catch (Exception e) {
            fileStorageService.delete(coverUrl);
            fileStorageService.delete(fileUrl);
            throw new RuntimeException(e);
        }

        return OkResponseResult.result().addMap(ArtworkConstant.ARTWORK_ID_RESPONSE_KEY, artworkBase.getId());
    }

    public String uploadContent(MultipartFile multipartFile) {
        String fileUrl = null;
        try {
            fileUrl = fileStorageService.uploadImgFile(MinioConstants.ARTWORK_CONTENT_PREFIX, getRandomFileName(Objects.requireNonNull(multipartFile.getOriginalFilename())), multipartFile.getInputStream());
            log.info("upload file to minio success! fileUrl = {}", fileUrl);
        } catch (IOException e) {
            log.error("Failed to upload file to minio, reason: {}", e.getMessage());
        }

        return fileUrl;
    }

    /**
     * 使用原始文件生成带水印的封面后，上传封面
     *
     * @param multipartFile 原始文件
     * @return 响应结果
     */
    public String uploadCover(MultipartFile multipartFile) {
        byte[] outputStream = generateCover(multipartFile);
        InputStream is = new ByteArrayInputStream(outputStream);
        String coverUrl = null;
        coverUrl = fileStorageService.uploadImgFile(MinioConstants.ARTWORK_COVER_PREFIX, getRandomFileName(Objects.requireNonNull(multipartFile.getOriginalFilename())), is);
        log.info("upload cover to minio success! coverUrl = {}", coverUrl);
        return coverUrl;
    }

    /**
     * 生成带水印的封面
     *
     * @param multipartFile 原始文件
     * @return 带水印的封面
     */
    public byte[] generateCover(MultipartFile multipartFile) {
        String randomFileName = getRandomFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(multipartFile.getInputStream())
                    .outputQuality(ArtworkConstant.COVER_OUTPUT_QUALITY)
                    .scale(ArtworkConstant.COVER_SCALE)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(ResourceUtils.getFile(ArtworkConstant.WATERMARK_PATH)), ArtworkConstant.COVER_OUTPUT_QUALITY)
                    .toOutputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error while generating thumbnail", e);
        }
        return outputStream.toByteArray();
    }

    public String getRandomFileName(String originalFilename) {
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String postfix = originalFilename.substring(originalFilename.indexOf("."));
        return fileName + postfix;
    }
}
