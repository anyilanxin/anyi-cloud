

package com.anyilanxin.skillfull.storage.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.anyilanxin.skillfull.storage.core.config.properties.OssProperty;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * oss工具类
 *
 * @author zxh
 * @date 2020-10-26 11:21
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AliOssFileUtils {
    private static AliOssFileUtils utils;
    private final OssProperty ossProperty;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 创建OSSClient
     *
     * @return OSSClient ${@link OSSClient}
     * @author zxh
     * @date 2020-10-26 11:26
     */
    public static OSSClient createOssClient() {
        return createOssClient("");
    }


    /**
     * 创建OSSClient
     *
     * @param endpoint ${@link String} oss endpoint
     * @return OSSClient ${@link OSSClient}
     * @author zxh
     * @date 2020-10-26 11:26
     */
    public static OSSClient createOssClient(String endpoint) {
        OssProperty ossProperty = utils.ossProperty;
        if (StringUtils.isBlank(endpoint)) {
            endpoint = ossProperty.getEndpoint();
        }
        OSS build = new OSSClientBuilder().build(endpoint, ossProperty.getAccessKey(), ossProperty.getSecretKey());
        return (OSSClient) build;
    }
}
