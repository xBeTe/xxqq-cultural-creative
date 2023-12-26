package com.xxz.artwork;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author xzxie
 * @create 2023/12/16 22:05
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ThumbnailTest {

    @Test
    public void testGenerateCover() throws IOException {

        Thumbnails.of(new File("G:\\Find\\壁纸\\倪妮\\b3a7e737ly1h0qmso40mzj254g3ggnpk.jpg"))
                .scale(0.5)
                .outputQuality(0.5f)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(ResourceUtils.getFile("classpath:xxqq-watermark.png")), 0.5f)
                .toFile(new File("G:\\cover05.jpg"));
    }

    
}
