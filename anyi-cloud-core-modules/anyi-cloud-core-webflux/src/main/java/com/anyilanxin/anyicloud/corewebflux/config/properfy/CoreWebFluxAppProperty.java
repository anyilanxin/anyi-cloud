

package com.anyilanxin.anyicloud.corewebflux.config.properfy;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * app节点配置信息
 *
 * @author zxh
 * @date 2020-06-29 00:25
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "app")
public class CoreWebFluxAppProperty implements Serializable {
    private static final long serialVersionUID = 713575253040294540L;

    /**
     * 运行环境
     */
    @Value("${spring.profiles.active}")
    private String active = "dev";

    /**
     * 服务名称
     */
    @Value("${spring.application.name:skillfull}")
    private String serviceName;

    /**
     * 当前配置文件路径
     */
    @Value(value = "classpath:application-${spring.profiles.active}.yml")
    private Resource resource;

    /**
     * 请求前缀
     */
    @Value("${spring.webflux.base-path:/}")
    private String basePath;

    /**
     * 服务器ip
     */
    @Value("${spring.cloud.nacos.discovery.ip:}")
    private String ip;

    /**
     * 是否生成外置配置文件
     */
    private boolean createOutConf = false;

    public String getIp() {
        if (StringUtils.isBlank(ip)) {
            String machineIp = "localhost";
            try {
                machineIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ignored) {
            }
            ip = machineIp;
        }
        return ip;
    }
}
