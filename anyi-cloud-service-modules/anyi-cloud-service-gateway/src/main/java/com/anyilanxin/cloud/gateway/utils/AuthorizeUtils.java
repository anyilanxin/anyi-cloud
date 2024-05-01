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

package com.anyilanxin.cloud.gateway.utils;

import com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.cloud.oauth2common.config.properties.AnYiResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 权限相关工具类
 *
 * @author zxh
 * @date 2022-03-03 23:17
 * @since 1.0.0
 */
public class AuthorizeUtils {

    /**
     * 获取网关配置白名单
     */
    public static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> createWhiteList(AnYiResourceProperties securityProperties) {
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> configAttributes = new LinkedHashMap<>(64);
        if (CollUtil.isNotEmpty(securityProperties.getCommonWhiteList())) {
            Set<String> whiteList = securityProperties.getCommonWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        }
        if (CommonCoreConstant.PRO.equalsIgnoreCase(securityProperties.getActive()) && CollUtil.isNotEmpty(securityProperties.getProWhiteList())) {
            Set<String> whiteList = securityProperties.getProWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        } else if (CommonCoreConstant.TEST.equalsIgnoreCase(securityProperties.getActive()) && CollUtil.isNotEmpty(securityProperties.getTestWhiteList())) {
            Set<String> whiteList = securityProperties.getTestWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        } else if (CommonCoreConstant.DEV.equalsIgnoreCase(securityProperties.getActive()) && CollUtil.isNotEmpty(securityProperties.getDevWhiteList())) {
            Set<String> whiteList = securityProperties.getDevWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        }
        return configAttributes;
    }


    private static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> urlToAttribute(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> configAttributeInfo = new LinkedHashMap<>();
        url = url.trim();
        String[] urlInfoArray = url.split(";");
        url = urlInfoArray[0];
        if (urlInfoArray.length == 2) {
            String methodInfo = urlInfoArray[1];
            String[] methods = methodInfo.split("[,，]");
            for (String method : methods) {
                if (StringUtils.isNotBlank(method)) {
                    HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(url, httpMethod.name());
                    List<ConfigAttribute> configAttributes = SecurityConfig.createList("permitAll()");
                    configAttributeInfo.put(matcher, configAttributes);
                } else {
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
                    List<ConfigAttribute> configAttributes = SecurityConfig.createList("permitAll()");
                    configAttributeInfo.put(matcher, configAttributes);
                }
            }
        } else {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
            List<ConfigAttribute> configAttributes = SecurityConfig.createList("permitAll()");
            configAttributeInfo.put(matcher, configAttributes);
        }
        return configAttributeInfo;
    }
}
