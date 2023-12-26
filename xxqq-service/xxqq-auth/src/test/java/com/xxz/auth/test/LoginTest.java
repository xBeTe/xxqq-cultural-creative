package com.xxz.auth.test;

import com.xxz.auth.service.LoginService;
import com.xxz.common.jwt.JwtProperties;
import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dtos.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xzxie
 * @create 2023/11/20 17:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class LoginTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void testPasswordEncoder() {
        String encodePwd = passwordEncoder.encode("123456");
        log.info("password: {}", encodePwd);
        // $2a$10$JPXiRi2Fe0NeAqOd4XHBPOG9qIrlvCgo90ISuNi4UBvaU8LLl8OB.
    }

    @Test
    public void testPasswordEncoderMatch() {
        String encodePwd = "$2a$10$JPXiRi2Fe0NeAqOd4XHBPOG9qIrlvCgo90ISuNi4UBvaU8LLl8OB.";
        boolean matches = passwordEncoder.matches("123456", encodePwd);
        log.info("matches: {}", matches);
    }

    @Autowired
    private LoginService loginService;

    @Test
    public void testLogin() {
        LoginUserDTO dto = new LoginUserDTO();
        dto.setUsername("xxz");
        dto.setPassword("123456");
        ResponseResult result = loginService.login(dto);
        log.info("login: {}", result);
    }

}
