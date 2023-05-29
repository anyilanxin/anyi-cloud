

package com.anyilanxin.skillfull.storage.core.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传oss配置
 *
 * @author zxh
 * @date 2020-10-23 13:09
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "alibaba.cloud.oss")
public class OssProperty {
    /**
     * 默认bucket
     */
    private String bucket;

    /**
     * 生成文件url访问过期时间(单位:s,默认12小时)
     */
    private long accessExpireTime = 43200L;

    /**
     * oss endpoint
     */
    private String endpoint;

    /**
     * accessKey
     */
    @Value("${alibaba.cloud.access-key}")
    private String accessKey;

    /**
     * secretKey
     */
    @Value("${alibaba.cloud.secret-key}")
    private String secretKey;
}
