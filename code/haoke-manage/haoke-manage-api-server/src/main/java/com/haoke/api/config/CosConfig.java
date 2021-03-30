package com.haoke.api.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Auspice Tian
 * @time 2021-03-19 16:55
 * @current haoke-manage-com.haoke.api.config
 */

@Data
@Configuration
@PropertySource(value = {"classpath:tencent.properties"})
@ConfigurationProperties(prefix = "tencent.cos")
public class CosConfig {
    private String appId;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String regionId;
    private String baseUrl;

    @Bean
    public COSClient cosClient() {
        //1. 初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(this.secretId, this.secretKey);

        //2. 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(this.regionId));

        //3. 生成cos客户端
        COSClient cosClient = new COSClient(cred,clientConfig);

        return  cosClient;
    }
}
