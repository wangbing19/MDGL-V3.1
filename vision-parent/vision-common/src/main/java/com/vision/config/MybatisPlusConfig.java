package com.vision.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description MybatisPlus拦截器
 * @Author lihd
 * @Date 2019/8/9 19:35
 * @Version 3.1
 */
@Configuration
@MapperScan(value = "com.vision.mapper")
public class MybatisPlusConfig {
    @Bean
    public SqlInterceptor sqlInterceptor() {
        return new SqlInterceptor();
    }
}

