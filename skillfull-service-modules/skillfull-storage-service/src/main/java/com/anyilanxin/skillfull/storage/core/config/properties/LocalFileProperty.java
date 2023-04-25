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
package com.anyilanxin.skillfull.storage.core.config.properties;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.SLASH;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* 文件上传本地配置
*
* @author zxiaozhou
* @date 2020-10-23 13:09
* @since JDK11
*/
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "local")
public class LocalFileProperty {

    /** 文件虚拟映射路径 */
    private String virtualMapping;

    /** 文件上传保存路径 */
    private String uploadFolder;

    /** 系统context-path */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String getVirtualMapping() {
        if (StringUtils.isNotBlank(virtualMapping) && virtualMapping.endsWith(SLASH)) {
            virtualMapping = virtualMapping.substring(0, virtualMapping.length() - 1);
        }
        return virtualMapping;
    }

    public String getUploadFolder() {
        if (StringUtils.isNotBlank(uploadFolder) && uploadFolder.endsWith(SLASH)) {
            uploadFolder = uploadFolder.substring(0, uploadFolder.length() - 1);
        }
        return uploadFolder;
    }

    public String getContextPath() {
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "";
        }
        return contextPath;
    }
}
