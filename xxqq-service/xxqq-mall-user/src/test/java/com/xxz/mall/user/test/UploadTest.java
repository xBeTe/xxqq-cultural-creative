package com.xxz.mall.user.test;

import com.xxz.common.constants.MinioConstants;
import com.xxz.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author xzxie
 * @create 2023/12/14 22:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UploadTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testUpload() {


        // 读取 resource 目录下的 avatar，jpeg 为输入流
        String originalFile = "avatar.jpg";
        InputStream is = UploadTest.class.getClassLoader().getResourceAsStream(originalFile);
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String postfix = originalFile.substring(originalFile.indexOf("."));
        String url = fileStorageService.uploadImgFile(MinioConstants.USER_AVATAR_PREFIX, fileName + postfix, is);
        log.info("upload avatar success, file url = {}", url);

    }
}
