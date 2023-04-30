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

package com.anyilanxin.anyicloud.corewebflux.config;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corewebflux.config.properfy.CoreWebFluxAppProperty;
import com.anyilanxin.anyicloud.corewebflux.config.properfy.SpringDocCoreWebFluxProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping;

/**
 * spring doc配置
 *
 * @author 安一老厨
 * @date 2021-12-12 14:58
 * @since 1.0.0
 */
@AutoConfiguration
@RequiredArgsConstructor
public class SpringDocConfig {
    private final SpringDocCoreWebFluxProperty property;
    private final CoreWebFluxAppProperty fluxAppProperty;
    private final ApplicationContext applicationContext;
    private RequestMappingInfoHandlerMapping handlerMapping;

    @Autowired
    private void setRequestMappingInfoHandlerMapping(@Qualifier("requestMappingHandlerMapping") RequestMappingInfoHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }


    /**
     * 基本配置
     *
     * @param apiPrefix ${@link String}
     * @return OpenAPI ${@link OpenAPI}
     * @author 安一老厨
     * @date 2021-12-12 16:51
     */
    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.api-prefix}") String apiPrefix) {
        Set<String> headers = property.getHeaders();
        Map<String, SecurityScheme> securitySchemes = new HashMap<>(headers.size());
        List<SecurityRequirement> security = new ArrayList<>(headers.size());
        headers.forEach(v -> {
            securitySchemes.put(v, new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(v));
            security.add(new SecurityRequirement().addList(v));
        });
        int total = 0;
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            if (!isEffective(handlerMethod)) {
                continue;
            }
            total++;
        }
        return new OpenAPI().info(new Info().title(property.getTitle() + "(总计:" + total + ")").version(property.getVersion())).addServersItem(new Server().url(fluxAppProperty.getBasePath())).addServersItem(new Server().url(apiPrefix)).components(new Components().securitySchemes(securitySchemes)).security(security);
    }


    /**
     * 默认分组
     *
     * @return GroupedOpenApi ${@link GroupedOpenApi}
     * @author 安一老厨
     * @date 2021-12-12 16:51
     */
    @Bean
    public GroupedOpenApi defaultGroup() {
        return GroupedOpenApi.builder().group("all").packagesToScan(property.getPackagesToScan().split("[,，]")).build();
    }


    /**
     * 其他分组
     *
     * @author 安一老厨
     * @date 2021-12-12 16:51
     */
    @Bean
    public void registerOtherGroupBean() {
        Map<String, DocInfoModel> docInfoModelMap = new HashMap<>(64);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            if (!isEffective(handlerMethod)) {
                continue;
            }
            Operation annotation = handlerMethod.getMethod().getAnnotation(Operation.class);
            String[] tags = annotation.tags();
            for (String tag : tags) {
                String beanName = tag.replaceAll("\\.", "");
                DocInfoModel docInfoModel = docInfoModelMap.get(beanName);
                if (Objects.isNull(docInfoModel)) {
                    docInfoModel = new DocInfoModel();
                    docInfoModel.setTotal(1);
                    docInfoModel.setVersion(tag);
                } else {
                    docInfoModel.setTotal(docInfoModel.getTotal() + 1);
                }
                docInfoModelMap.put(beanName, docInfoModel);
            }
        }
        if (CollUtil.isNotEmpty(docInfoModelMap)) {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            docInfoModelMap.forEach((k, v) -> {
                GroupedOpenApi build = GroupedOpenApi.builder().group(v.getVersion()).packagesToScan(property.getPackagesToScan().split("[,，]")).addOpenApiCustomiser(sv -> sv.setInfo(new Info().title(property.getTitle() + "(总计:" + v.getTotal() + ")").version(v.getVersion()))).addOpenApiMethodFilter(sv -> {
                    Class<?> beanType = sv.getClass();
                    Hidden hidden = beanType.getAnnotation(Hidden.class);
                    if (Objects.nonNull(hidden)) {
                        return false;
                    }
                    Operation annotation = sv.getAnnotation(Operation.class);
                    if (Objects.isNull(annotation)) {
                        return false;
                    }
                    String[] tagInfos = annotation.tags();
                    if (Objects.isNull(tagInfos) || tagInfos.length <= 0) {
                        return false;
                    }
                    return Arrays.asList(tagInfos).contains(v.getVersion());
                }).build();
                defaultListableBeanFactory.registerSingleton(k, build);
            });
        }
    }


    /**
     * 判断接口是否有效
     */
    private boolean isEffective(HandlerMethod handlerMethod) {
        Class<?> beanType = handlerMethod.getBeanType();
        Hidden hidden = beanType.getAnnotation(Hidden.class);
        if (Objects.nonNull(hidden)) {
            return false;
        }
        Operation annotation = handlerMethod.getMethod().getAnnotation(Operation.class);
        return Objects.nonNull(annotation) && !annotation.hidden();
    }

    /**
     * doc信息
     *
     * @author 安一老厨
     * @date 2022-04-22 00:12
     * @since 1.0.0
     */
    @Getter
    @Setter
    public static class DocInfoModel {
        /**
         * 当前版本总数
         */
        private int total;
        /**
         * 版本
         */
        private String version;
    }
}
