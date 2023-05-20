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

package com.anyilanxin.anyicloud.corecommon.utils;

import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 本地化工具
 *
 * @author zxh
 * @date 2022-02-16 15:18
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class I18nUtil {
    private final MessageSource messageSource;
    private static I18nUtil util;

    @PostConstruct
    public void init() {
        util = this;
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code ${@link String} the code of the message
     * @return String ${@link String}
     * @author zxh
     * @date 2022-02-16 17:17
     */
    public static String get(String code) {
        try {
            return util.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return code;
        }
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code           ${@link String} the code of the message
     * @param defaultMessage ${@link String} the String to return if the lookup fails
     * @return String ${@link String}
     * @author zxh
     * @date 2022-02-16 17:17
     */
    public static String getDefault(String code, String defaultMessage) {
        try {
            return util.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code ${@link String} the code of the message
     * @param args ${@link String[]} arguments for the message, or null if none
     * @return String ${@link String}
     * @author zxh
     * @date 2022-02-16 17:17
     */
    public static String get(String code, @Nullable Object... args) {
        try {
            return util.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return code;
        }
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code           ${@link String} the code of the message
     * @param args           ${@link String[]} arguments for the message, or null if none
     * @param defaultMessage ${@link String} the String to return if the lookup fails
     * @return String ${@link String}
     * @author zxh
     * @date 2022-02-16 17:17
     */
    public static String getDefault(String code, String defaultMessage, @Nullable Object... args) {
        try {
            return util.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }


    /**
     * i18n兼容处理
     *
     * @param errMsg ${@link String}
     * @param local  ${@link Map <String, String>} 替换编码
     * @return String ${@link String}
     * @author zxh
     * @date 2022-02-16 11:36
     */
    public static String getLocalMessage(Map<String, String> local, String errMsg) {
        if (StringUtils.isNotBlank(errMsg) && ChineseCheckUtils.isContainChinese(errMsg)) {
            return errMsg;
        }
        Set<Map.Entry<String, String>> entries = local.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            errMsg = errMsg.replace(entry.getKey(), I18nUtil.get(entry.getValue()));
        }
        return errMsg;
    }
}
