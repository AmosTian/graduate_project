package com.haoke.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Auspice Tian
 * @time 2021-04-25 14:01
 * @current haoke-manage-com.haoke.api.config
 */

/*
* 杂配置
* */
@Configuration
public class HaokeConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
