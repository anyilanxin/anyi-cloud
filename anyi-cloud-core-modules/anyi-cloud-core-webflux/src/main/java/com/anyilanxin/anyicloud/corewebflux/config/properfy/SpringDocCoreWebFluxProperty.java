

package com.anyilanxin.anyicloud.corewebflux.config.properfy;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.GRAY_HEADER_KEY;

import cn.hutool.core.collection.CollectionUtil;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * spring doc配置
 *
 * @author zxh
 * @date 2020-09-11 03:16
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocCoreWebFluxProperty {
    /**
     * 请求头
     */
    private Set<String> headers;

    /**
     * 是否为webflux,用于spring doc打印信息路径判断使用
     */
    private boolean webflux;

    /**
     * 版本号
     */
    private String version;

    /**
     * swagger请求前缀
     */
    private String apiPrefix;

    /**
     * 联系人
     */
    private String contactUser = "zxiaozhou";

    /**
     * 联系邮箱
     */
    private String contactEmail = "";

    /**
     * 标题
     */
    private String title = "";

    /**
     * 是否启用doc
     */
    @Value("${springdoc.swagger-ui.enabled}")
    private boolean swaggerEnable;

    /**
     * 扫包路径
     */
    private String packagesToScan;

    public Set<String> getHeaders() {
        if (CollectionUtil.isEmpty(headers)) {
            headers = new HashSet<>();
        }
        headers.add("Authorization");
        // 灰度信息key
        headers.add(GRAY_HEADER_KEY);
        return headers;
    }
}
