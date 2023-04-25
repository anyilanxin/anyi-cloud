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
package com.anyilanxin.skillfull.coremvc.config;

import com.anyilanxin.skillfull.coremvc.config.properties.CoreWebMvcProperty;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
* 以jar包启动时创建外置配置文件
*
* @author zxiaozhou
* @date 2019-04-16 10:38
* @since JDK11
*/
@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyStartCoreMvcConfig implements ApplicationRunner {
    private final CoreWebMvcProperty property;

    @Override
    public void run(ApplicationArguments args) {
        // 处理生成外置配置文件
        if (property.isCreateOutConf()) {
            // 检测外置配置文件路径
            File file = new File("./config");
            if (!file.exists() || !file.isDirectory()) {
                boolean result = file.mkdirs();
                if (result) {
                    log.debug("------------StartConfig------------>createOutConfig:{}", "创建外置配置文件路径成功");
                } else {
                    throw new RuntimeException("创建外置配置文件路径失败，请检查环境");
                }
            }
            // 移动当前配置文件到外置配置文件中
            String application = "application-" + property.getActive() + ".yml";
            file = new File("./config/" + application);
            if (!file.exists()) {
                try (OutputStream outputStream = new FileOutputStream(file);
                        InputStream inputStream = property.getResource().getInputStream()) {
                    IOUtils.copy(inputStream, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
