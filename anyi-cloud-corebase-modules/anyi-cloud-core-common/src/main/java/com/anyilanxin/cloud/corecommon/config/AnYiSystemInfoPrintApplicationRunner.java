/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.corecommon.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 打印启动信息
 *
 * @author zhou
 * @date 2022-07-17 23:03
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AnYiSystemInfoPrintApplicationRunner implements ApplicationRunner {
    private final Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("------------------发生了错误------run--->{}", e.getMessage());
            e.printStackTrace();
        }
        String port = environment.getProperty("server.port");
        // 先主动判断一次是不是webflux
        boolean webflux = true;
        String path = environment.getProperty("spring.webflux.base-path", "");
        if (StringUtils.isBlank(path)) {
            webflux = false;
            path = environment.getProperty("server.servlet.context-path", "");
        }
        // 最后再判断是否配置设置的为webflux
        if (Boolean.TRUE.equals(environment.getProperty("springdoc.webflux", Boolean.class))) {
            webflux = true;
            path = environment.getProperty("spring.webflux.base-path", "");
        }
        String swaggerUrl = environment.getProperty("springdoc.swagger-ui.path");
        String customUrl = environment.getProperty("springdoc.swagger-ui.custom-path");
        if (StringUtils.isNotBlank(customUrl)) {
            swaggerUrl = customUrl;
        }
        if (StringUtils.isBlank(swaggerUrl)) {
            if (webflux) {
                swaggerUrl = path + "/webjars/swagger-ui/index.html";
            } else {
                swaggerUrl = path + "/swagger-ui/index.html";
            }
        }
        String profilesActive = environment.getProperty("spring.profiles.active");
        String version = environment.getProperty("spring.application.version");
        String projectName = environment.getProperty("spring.application.name");
        String template = """


                =======================================================================================================================

                    AnYi Cloud Enterprise Application（%s v%s %s）is running! Access URLs:

                           ___      __  ___    __               _  __ _
                          /   |  ___\\ \\/ (_)  / /   ____ _____ | |/ /(_)___
                         / /| | / __ \\  / /  / /   / __ `/ __ \\|   // / __ \\
                        / ___ |/ / / / / /  / /___/ /_/ / / / /   |/ / / / /
                       /_/  |_/_/ /_/_/_/  /_____/\\__,_/_/ /_/_/|_/_/_/ /_/

                        安一兰心(AN YI LAN XIN)


                    Website Preview:\thttps://anyilanxin.com
                    Api Url  Prefix:\thttp://%s:%s%s
                    Spring  Doc  Ui:\thttp://%s:%s%s

                =======================================================================================================================
                """;
        String info = template.formatted(projectName, version, StringUtils.isNotBlank(profilesActive) ? profilesActive : "", ip, port, path, ip, port, swaggerUrl);
        log.info(info);
        // @formatter:off
    }
}
