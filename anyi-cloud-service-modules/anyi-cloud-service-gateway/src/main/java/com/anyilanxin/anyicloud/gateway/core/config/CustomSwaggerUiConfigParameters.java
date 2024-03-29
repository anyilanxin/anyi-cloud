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

package com.anyilanxin.anyicloud.gateway.core.config;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.anyicloud.corecommon.model.system.SwaggerConfigModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.HTTP;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;

/**
 * @author zxh
 * @date 2022-01-05 02:26
 * @since 1.0.0
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = Constants.SPRINGDOC_ENABLED, matchIfMissing = true)
@ConditionalOnBean(SpringDocConfiguration.class)
public class CustomSwaggerUiConfigParameters extends SwaggerUiConfigParameters {
    private final SwaggerUiConfigProperties swaggerUiConfig;
    private final DiscoveryClient disClient;
    private final static String MANAGEMENT_CONTEXT_PATH = "management.context-path";
    private final static String ACTUATOR = "/actuator";
    private final WebClient client;
    @Value(value = "${springdoc.api-docs.path:" + Constants.DEFAULT_API_DOCS_URL + "}")
    private String swaggerConfig;
    @Value("${spring.application.name:anyi}")
    private String applicationName;
    @Value("${springdoc.other-api-docs-prefix:}")
    private String otherApiDocsPrefix;
    @Value("${springdoc.gateway-api-docs-prefix:}")
    private String gatewayApiDocsPrefix;
    private final Map<String, SwaggerUrl> apiDocInfoUrlModel;

    public CustomSwaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfig, DiscoveryClient disClient, WebClient.Builder loadBalancedWebClientBuilder, Map<String, SwaggerUrl> apiDocInfoUrlModel) {
        super(swaggerUiConfig);
        this.swaggerUiConfig = swaggerUiConfig;
        this.disClient = disClient;
        this.client = loadBalancedWebClientBuilder.build();
        this.apiDocInfoUrlModel = CollUtil.isEmpty(apiDocInfoUrlModel) ? new HashMap<>() : apiDocInfoUrlModel;
    }


    @Override
    public Map<String, Object> getConfigParameters() {
        super.urls = getSwaggerUrl();
        return super.getConfigParameters();
    }


    /**
     * 动态获取url
     *
     * @return Set<SwaggerUrl> ${@link Set<SwaggerUrl>}
     * @author zxh
     * @date 2022-01-05 02:39
     */
    private Set<SwaggerUrl> getSwaggerUrl() {
        Set<SwaggerUrl> gatewayUrls = swaggerUiConfig.getUrls();
        // 初始化网关url
        if (CollUtil.isNotEmpty(gatewayUrls)) {
            gatewayUrls.forEach(v -> {
                String url = v.getUrl();
                if (StringUtils.isNotBlank(url)) {
                    if (!url.startsWith(gatewayApiDocsPrefix)) {
                        url = gatewayApiDocsPrefix + url;
                        v.setUrl(url);
                    }
                    if (StringUtils.isBlank(v.getName())) {
                        v.setName(applicationName);
                        v.setDisplayName(applicationName);
                    } else if (!v.getName().startsWith(applicationName)) {
                        v.setName(applicationName + "(" + v.getName() + ")");
                        v.setDisplayName(v.getName());
                    }
                    apiDocInfoUrlModel.put(v.getName(), v);
                }
            });
        }
        // 清除历史服务不存在的doc
        Map<String, SwaggerUrl> middleApiDocInfoUrlModel = new HashMap<>();
        Set<String> services = new HashSet<>(disClient.getServices());
        if (CollUtil.isNotEmpty(apiDocInfoUrlModel)) {
            Set<Map.Entry<String, SwaggerUrl>> entries = apiDocInfoUrlModel.entrySet();
            for (Map.Entry<String, SwaggerUrl> entry : entries) {
                String key = entry.getKey();
                List<String> collect = services.stream().filter(key::startsWith).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(collect)) {
                    middleApiDocInfoUrlModel.put(key, entry.getValue());
                }
            }
        }
        apiDocInfoUrlModel.clear();
        apiDocInfoUrlModel.putAll(middleApiDocInfoUrlModel);
        // 获取内部非网关系统swagger信息
        Set<String> otherServices = services.stream().filter(v -> !v.equals(applicationName)).collect(Collectors.toSet());
        ParameterizedTypeReference<AnYiResult<Map<String, ManageSwaggerInfoModel>>> reference = new ParameterizedTypeReference<>() {
        };
        client.get().uri(HTTP + ServiceConstant.SYSTEM_SERVICE + ServiceConstant.SYSTEM_SERVICE_PATH + "/manage-service/select/swagger-info")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(reference))
                .onErrorStop()
                .filter(result -> result.isSuccess() && CollUtil.isNotEmpty(result.getData()))
                .map(AnYiResult::getData)
                .doOnNext(swaggerInfoData -> {
                    otherServices.forEach(v -> {
                        List<ServiceInstance> instances = disClient.getInstances(v);
                        if (CollUtil.isNotEmpty(instances)) {
                            instances.forEach(sv -> {
                                String serviceId = sv.getServiceId();
                                ManageSwaggerInfoModel manageSwaggerInfoModel = swaggerInfoData.get(serviceId);
                                if (Objects.nonNull(manageSwaggerInfoModel)) {
                                    Map<String, String> metadata = sv.getMetadata();
                                    String contextPath = metadata.get(MANAGEMENT_CONTEXT_PATH);
                                    contextPath = StringUtils.isBlank(contextPath) ? "" : contextPath;
                                    contextPath = contextPath.replace(ACTUATOR, "");
                                    client.mutate().build().get().uri(HTTP + serviceId + contextPath + swaggerConfig + DEFAULT_PATH_SEPARATOR + Constants.SWAGGGER_CONFIG_FILE)
                                            .exchangeToMono(response -> response.bodyToMono(SwaggerConfigModel.class))
                                            .onErrorStop()
                                            .filter(body -> CollUtil.isNotEmpty(body.getUrls()))
                                            .map(SwaggerConfigModel::getUrls)
                                            .doOnNext(urlInfos -> urlInfos.forEach(urlInfo -> {
                                                SwaggerUrl otherSwaggerUrl = new SwaggerUrl();
                                                otherSwaggerUrl.setUrl(otherApiDocsPrefix + urlInfo.getUrl());
                                                otherSwaggerUrl.setDisplayName(serviceId + "(" + urlInfo.getName() + ")");
                                                otherSwaggerUrl.setName(serviceId + "(" + urlInfo.getName() + ")");
                                                apiDocInfoUrlModel.put(otherSwaggerUrl.getName(), otherSwaggerUrl);
                                            })).subscribe();
                                }
                            });
                        }
                    });
                }).subscribe();
        Set<SwaggerUrl> urls = new HashSet<>(apiDocInfoUrlModel.size());
        apiDocInfoUrlModel.forEach((k, v) -> urls.add(v));
        return urls;
    }
}
