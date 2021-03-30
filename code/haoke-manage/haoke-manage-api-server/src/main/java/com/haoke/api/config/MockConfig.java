package com.haoke.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Auspice Tian
 * @time 2021-03-26 10:44
 * @current haoke-manage-com.haoke.api
 */

@PropertySource("classpath:mock-data.properties")
@ConfigurationProperties(prefix = "mock")
@Configuration
@Data
public class MockConfig {
    private String indexMenu;
    private String indexInfo;
    private String indexFaq;
    private String indexHouse;
    private String infosList1;
    private String infosList2;
    private String infosList3;
    private String my;
}
