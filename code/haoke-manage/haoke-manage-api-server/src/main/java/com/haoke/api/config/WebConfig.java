package com.haoke.api.config;

import com.haoke.api.interceptor.RedisInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Auspice Tian
 * @time 2021-03-30 22:47
 * @current haoke-manage-com.haoke.api.config
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RedisInterceptor redisInterceptor;

    //将自定义interceptor注册到Web容器中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有请求都要经过拦截器
        registry.addInterceptor(redisInterceptor).addPathPatterns("/**");
    }
}
