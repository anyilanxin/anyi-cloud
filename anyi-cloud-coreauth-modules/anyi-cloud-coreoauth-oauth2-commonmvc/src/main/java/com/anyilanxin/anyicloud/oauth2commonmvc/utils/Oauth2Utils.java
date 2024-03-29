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

package com.anyilanxin.anyicloud.oauth2commonmvc.utils;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.oauth2common.annotation.GatewayPreAuthorize;
import com.anyilanxin.anyicloud.oauth2common.config.properties.AnYiResourceProperties;
import com.anyilanxin.anyicloud.oauth2common.exception.AnYiCredentialsExpiredException;
import com.anyilanxin.anyicloud.oauth2common.model.GatewayUrlAuthInfo;
import com.anyilanxin.anyicloud.oauth2common.store.AnYiAuthentication;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * oauth2工具类
 *
 * @author zxh
 * @date 2022-03-01 23:57
 * @since 1.0.0
 */
public class Oauth2Utils {

    /**
     * 获取白名单访问的接口
     *
     * @return Set<String> ${@link Set <String>}
     * @author zxh
     * @date 2022-02-19 13:11
     */
    public static Set<WhiteListInfo> getWhiteList(ApplicationContext applicationContext, AnYiResourceProperties properties) {
        String active = properties.getActive();
        // 处理匿名用户访问
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<WhiteListInfo> whiteList = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            Secured secured = handlerMethod.getMethodAnnotation(Secured.class);
            if (Objects.isNull(preAuthorize) && Objects.isNull(secured)) {
                continue;
            }
            if (Objects.nonNull(preAuthorize) && (preAuthorize.value().contains("isAnonymous()") || preAuthorize.value().contains("permitAll()"))) {
                WhiteListInfo whiteListInfo = requestMappingToWhite(infoEntry.getKey());
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                    continue;
                }
            }
            if (Objects.nonNull(secured)) {
                String[] securedValues = secured.value();
                if (Objects.nonNull(securedValues) && securedValues.length > 0 && Arrays.asList(securedValues).contains("ROLE_ANONYMOUS")) {
                    WhiteListInfo whiteListInfo = requestMappingToWhite(infoEntry.getKey());
                    if (Objects.nonNull(whiteListInfo)) {
                        whiteList.add(whiteListInfo);
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(properties.getCommonWhiteList())) {
            properties.getCommonWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        }
        if (CommonCoreConstant.PRO.equalsIgnoreCase(active) && CollUtil.isNotEmpty(properties.getProWhiteList())) {
            properties.getProWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        } else if (CommonCoreConstant.TEST.equalsIgnoreCase(active) && CollUtil.isNotEmpty(properties.getTestWhiteList())) {
            properties.getTestWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        } else if (CommonCoreConstant.DEV.equalsIgnoreCase(active) && CollUtil.isNotEmpty(properties.getDevWhiteList())) {
            properties.getDevWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        }
        return whiteList;
    }

    /**
     * 白名单信息
     *
     * @author zxh
     * @date 2022-02-23 22:12
     * @since 1.0.0
     */
    @Getter
    @Setter
    public static class WhiteListInfo {
        /**
         * url信息
         */
        private Set<String> urls;

        /**
         * url对应请求方法
         */
        private Set<HttpMethod> methods;
    }

    /**
     * RequestMappingInfo转WhiteListInfo
     *
     * @param mappingInfo ${@link RequestMappingInfo}
     * @return WhiteListInfo ${@link WhiteListInfo}
     * @author zxh
     * @date 2022-02-23 22:23
     */
    private static WhiteListInfo requestMappingToWhite(RequestMappingInfo mappingInfo) {
        PathPatternsRequestCondition pathPatternsCondition = mappingInfo.getPathPatternsCondition();
        if (Objects.nonNull(pathPatternsCondition) && CollUtil.isNotEmpty(pathPatternsCondition.getPatternValues())) {
            WhiteListInfo whiteListInfo = new WhiteListInfo();
            whiteListInfo.setUrls(pathPatternsCondition.getPatternValues());
            RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
            if (CollUtil.isNotEmpty(methodsCondition.getMethods())) {
                Set<RequestMethod> methods = methodsCondition.getMethods();
                Set<HttpMethod> methodSet = new HashSet<>(methods.size());
                for (RequestMethod requestMethod : methods) {
                    String name = requestMethod.name();
                    HttpMethod httpMethod = HttpMethod.valueOf(name.toUpperCase());
                    methodSet.add(httpMethod);
                }
                if (CollUtil.isNotEmpty(methodSet)) {
                    whiteListInfo.setMethods(methodSet);
                }
            }
            return whiteListInfo;
        }
        return null;
    }


    /**
     * config配置url info to WhiteListInfo
     *
     * @param urlInfo ${@link String}
     * @return WhiteListInfo ${@link WhiteListInfo}
     * @author zxh
     * @date 2022-02-23 22:26
     */
    private static WhiteListInfo urlInfoToWhite(String urlInfo) {
        if (StringUtils.isNotBlank(urlInfo)) {
            WhiteListInfo whiteListInfo = new WhiteListInfo();
            String[] urlInfoArray = urlInfo.split(";");
            whiteListInfo.setUrls(Set.of(urlInfoArray[0]));
            if (urlInfoArray.length == 2) {
                String methodInfo = urlInfoArray[1];
                String[] methods = methodInfo.split("[,，]");
                Set<HttpMethod> methodSet = new HashSet<>(methods.length);
                for (String method : methods) {
                    if (StringUtils.isNotBlank(method)) {
                        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
                        methodSet.add(httpMethod);
                    }
                }
                if (CollUtil.isNotEmpty(methodSet)) {
                    whiteListInfo.setMethods(methodSet);
                }
            }
            return whiteListInfo;
        }
        return null;

    }


    /**
     * 获取汇聚网关鉴权信息
     *
     * @return Set<String> ${@link Set <String>}
     * @author zxh
     * @date 2022-02-19 13:11
     */
    public static Set<GatewayUrlAuthInfo> getGatewayAuthInfo(ApplicationContext applicationContext) {
        // 处理匿名用户访问
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<GatewayUrlAuthInfo> gatewayUrlAuthInfos = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            GatewayPreAuthorize gatewayPreAuthorize = handlerMethod.getMethodAnnotation(GatewayPreAuthorize.class);
            if (Objects.isNull(gatewayPreAuthorize) || StringUtils.isBlank(gatewayPreAuthorize.value())) {
                continue;
            }
            GatewayUrlAuthInfo whiteListInfo = requestMappingToAuthInfo(infoEntry.getKey(), gatewayPreAuthorize);
            if (Objects.nonNull(whiteListInfo)) {
                gatewayUrlAuthInfos.add(whiteListInfo);
            }
        }
        return gatewayUrlAuthInfos;
    }


    /**
     * 获取登录token值
     */
    public static String getTokenValue() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication principal = context.getAuthentication();
        if (principal instanceof AnYiAuthentication authentication) {
            return authentication.getTokenValue();
        }
        throw new AnYiCredentialsExpiredException("当前未授权或授权过期");
    }

    /**
     * RequestMappingInfo转WhiteListInfo
     *
     * @param mappingInfo         $
     * @param gatewayPreAuthorize
     * @return {@link GatewayUrlAuthInfo }
     * @author zxh
     * @date 2023-09-22 20:22:27
     */
    private static GatewayUrlAuthInfo requestMappingToAuthInfo(RequestMappingInfo mappingInfo, GatewayPreAuthorize gatewayPreAuthorize) {
        PathPatternsRequestCondition pathPatternsCondition = mappingInfo.getPathPatternsCondition();
        if (Objects.nonNull(pathPatternsCondition) && CollUtil.isNotEmpty(pathPatternsCondition.getPatternValues())) {
            GatewayUrlAuthInfo whiteListInfo = new GatewayUrlAuthInfo();
            whiteListInfo.setUrls(pathPatternsCondition.getPatternValues());
            RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
            if (CollUtil.isNotEmpty(methodsCondition.getMethods())) {
                Set<RequestMethod> methods = methodsCondition.getMethods();
                Set<HttpMethod> methodSet = new HashSet<>(methods.size());
                for (RequestMethod requestMethod : methods) {
                    String name = requestMethod.name();
                    HttpMethod httpMethod = HttpMethod.valueOf(name.toUpperCase());
                    methodSet.add(httpMethod);
                }
                if (CollUtil.isNotEmpty(methodSet)) {
                    whiteListInfo.setMethods(methodSet);
                }
            }
            whiteListInfo.setSecurityExpression(gatewayPreAuthorize.value());
            return whiteListInfo;
        }
        return null;
    }
}
