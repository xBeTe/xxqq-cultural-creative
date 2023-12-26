package com.xxz.mail.config;

import com.xxz.model.config.SwaggerProperties;
import com.xxz.common.swagger.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xzxie
 * @create 2023/11/8 16:46
 */
@Configuration
@EnableSwagger2
@Profile(value = {"dev", "default"})
public class SwaggerConfiguration extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
            return SwaggerProperties.builder()
                    .apiBasePackage("com.xxz.mail")
                    .title("小巷千秋邮件服务 APIs ")
                    .description("后台相关接口文档")
                    .contactName("XzXie")
                    .contactEmail("bete1031@qq.comm")
                    .contactUrl("")
                    .license("Open Source")
                    .licenseUrl("")
                    .version("0.1.0")
                    .enableSecurity(true)
                    .build();
    }
}
