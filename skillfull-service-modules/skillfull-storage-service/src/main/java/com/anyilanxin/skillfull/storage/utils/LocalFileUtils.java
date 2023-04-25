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

package com.anyilanxin.skillfull.storage.utils;

import com.anyilanxin.skillfull.storage.core.config.properties.LocalFileProperty;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 文件处理工具类
 *
 * @author zxiaozhou
 * @date 2021-02-23 09:48
 * @since JDK1.8
 */
@RequiredArgsConstructor
@Component
public class LocalFileUtils {
    private final LocalFileProperty fileProperty;
    private static LocalFileUtils utils;

    @PostConstruct
    private void init() {
        utils = this;
    }

    /**
     * 映射路径转磁盘路径
     *
     * @param mapPath ${@link String} 映射路径
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-02-23 09:51
     */
    public static String mapPathToDiskPath(String mapPath) {
        String path = mapPath.replaceFirst(utils.fileProperty.getVirtualMapping(), "");
        return utils.fileProperty.getUploadFolder() + path;
    }

    /**
     * 磁盘路径转映射路径
     *
     * @param diskPath ${@link String} 磁盘路径
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-02-23 09:51
     */
    public static String diskPathToMapPath(String diskPath) {
        String path = diskPath.replaceFirst(utils.fileProperty.getUploadFolder(), "");
        return utils.fileProperty.getVirtualMapping() + path;
    }
}
