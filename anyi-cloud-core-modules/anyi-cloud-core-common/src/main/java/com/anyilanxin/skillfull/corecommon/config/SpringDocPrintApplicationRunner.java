/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.corecommon.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 打印启动信息
 *
 * @author zhou
 * @date 2022-07-17 23:03
 * @since JDK11
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringDocPrintApplicationRunner implements ApplicationRunner {
    private final Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = environment.getProperty("server.port");
        boolean webflux = Boolean.TRUE.equals(environment.getProperty("springdoc.webflux", Boolean.class));
        String path;
        if (webflux) {
            path = environment.getProperty("spring.webflux.base-path", "");
        } else {
            path = environment.getProperty("server.servlet.context-path", "");
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
                swaggerUrl = path + "/swagger-ui.html";
            }
        }
        String profilesActive = environment.getProperty("spring.profiles.active");
        String version = environment.getProperty("spring.application.version");
        String projectName = environment.getProperty("spring.application.name");
        log.info("\n-----------------------------------------------------------------------------\n" + "SkillFull Cloud Application（" + projectName + " v" + version + " " + (StringUtils.isNotBlank(profilesActive) ? profilesActive : "") + "）is running! Access URLs:\n" + "\tWebsite Preview:\thttps://skillfull.divisu.com\n" + "\tApi Url Prefix:\t\thttp://" + ip + ":" + port + path + "\n" + "\tSwagger Ui:\t\t\thttp://" + ip + ":" + port + swaggerUrl + "\n" + "-----------------------------------------------------------------------------");
    }
}
