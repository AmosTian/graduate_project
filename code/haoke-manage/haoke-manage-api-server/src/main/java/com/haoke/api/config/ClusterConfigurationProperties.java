package com.haoke.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-30 13:58
 * @current haoke-manage-com.haoke.api.config
 */

/* 读取集群配置文件 */
@PropertySource(value = "classpath:application.properties")
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Component
@Data
public class ClusterConfigurationProperties {

    private List<String> nodes;
    private Integer maxRedirects;//集群节点最大重定向数
}
