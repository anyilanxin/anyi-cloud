// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import indi.zxiaozhou.skillfull.storage.core.config.properties.OssProperty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * oss工具类
 *
 * @author zxiaozhou
 * @date 2020-10-26 11:21
 * @since JDK11
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
