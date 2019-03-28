package com.superywd.lottery.manage.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author: 迷宫的中心
 * @date: 2019/3/28 13:20
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录请求拦截器
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/assets/**",
                        "/admin/login",
                        "/login.html");
    }
}
