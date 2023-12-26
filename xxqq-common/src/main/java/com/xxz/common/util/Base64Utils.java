package com.xxz.common.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xzxie
 * @create 2023/12/21 16:28
 */
@Slf4j
public class Base64Utils {

    public static String convertImageToBase64(String imgUrl) throws IOException {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();

            outStream = new ByteArrayOutputStream();
            // 创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return new BASE64Encoder().encode(outStream.toByteArray());
        } finally {
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("close input stream error", e);
                }
            }
            if(outStream != null)
            {
                try {
                    outStream.close();
                } catch (IOException e) {
                    log.error("close output stream error", e);
                }
            }
            if(httpUrl != null)
            {
                httpUrl.disconnect();
            }
        }
    }


    public static void main(String[] args) {
        String base64 = null;
        try {
            base64 = convertImageToBase64("http://xxz10:9000/xxqq/user/avatar/2023/12/19/bc16fdd049d4408782c02bbc2db69fb9.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(base64);
    }
}
