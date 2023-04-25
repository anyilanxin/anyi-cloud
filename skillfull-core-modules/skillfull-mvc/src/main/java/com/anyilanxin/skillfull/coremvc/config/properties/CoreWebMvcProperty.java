/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.coremvc.config.properties;

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
* @author zxiaozhou
* @date 2020-06-29 00:25
* @since JDK11
*/
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "app")
public class CoreWebMvcProperty implements Serializable {
    private static final long serialVersionUID = 713575253040294540L;

    /** 运行环境 */
    @Value("${spring.profiles.active}")
    private String active = "dev";

    /** 服务名称 */
    @Value("${spring.application.name:skillfull}")
    private String serviceName;

    /** 当前配置文件路径 */
    @Value(value = "classpath:application-${spring.profiles.active}.yml")
    private Resource resource;

    /** 请求前缀 */
    @Value("${server.servlet.context-path:/}")
    private String contentPath;

    /** 请求端口 */
    @Value("${server.port:8080}")
    private String port;

    /** 服务器ip */
    @Value("${spring.cloud.nacos.discovery.ip:}")
    private String ip;

    /** 是否生成外置配置文件 */
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
