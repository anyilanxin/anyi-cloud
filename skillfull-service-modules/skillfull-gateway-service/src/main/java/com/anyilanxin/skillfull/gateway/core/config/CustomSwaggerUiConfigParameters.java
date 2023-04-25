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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.gateway.core.config;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.HTTP;
import static org.springdoc.core.Constants.*;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.skillfull.corecommon.model.system.SwaggerConfigModel;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author zxiaozhou
 * @date 2022-01-05 02:26
 * @since JDK1.8
 */
@Lazy(false)
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = SPRINGDOC_SWAGGER_UI_ENABLED, matchIfMissing = true)
@ConditionalOnBean(SpringDocConfiguration.class)
public class CustomSwaggerUiConfigParameters extends SwaggerUiConfigParameters {
  private final SwaggerUiConfigProperties swaggerUiConfig;
  private final DiscoveryClient disClient;
  private static final String MANAGEMENT_CONTEXT_PATH = "management.context-path";
  private static final String ACTUATOR = "/actuator";
  private final WebClient.Builder webClient;

  @Value(value = "${springdoc.api-docs.path:" + DEFAULT_API_DOCS_URL + "}")
  private String swaggerConfig;

  @Value("${spring.application.name:skillfull}")
  private String applicationName;

  @Value("${springdoc.other-api-docs-prefix:}")
  private String otherApiDocsPrefix;

  @Value("${springdoc.gateway-api-docs-prefix:}")
  private String gatewayApiDocsPrefix;

  private final Map<String, SwaggerUrl> apiDocInfoUrlModel;

  public CustomSwaggerUiConfigParameters(
      SwaggerUiConfigProperties swaggerUiConfig,
      DiscoveryClient disClient,
      WebClient.Builder webClient,
      Map<String, SwaggerUrl> apiDocInfoUrlModel) {
    super(swaggerUiConfig);
    this.swaggerUiConfig = swaggerUiConfig;
    this.disClient = disClient;
    this.webClient = webClient;
    this.apiDocInfoUrlModel =
        CollUtil.isEmpty(apiDocInfoUrlModel) ? new HashMap<>() : apiDocInfoUrlModel;
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
   * @author zxiaozhou
   * @date 2022-01-05 02:39
   */
  private Set<SwaggerUrl> getSwaggerUrl() {
    Set<SwaggerUrl> gatewayUrls = swaggerUiConfig.getUrls();
    // 初始化网关url
    if (CollUtil.isNotEmpty(gatewayUrls)) {
      gatewayUrls.forEach(
          v -> {
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
        List<String> collect =
            services.stream().filter(key::startsWith).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
          middleApiDocInfoUrlModel.put(key, entry.getValue());
        }
      }
    }
    apiDocInfoUrlModel.clear();
    apiDocInfoUrlModel.putAll(middleApiDocInfoUrlModel);
    // 获取内部非网关系统swagger信息
    Set<String> otherServices =
        services.stream().filter(v -> !v.equals(applicationName)).collect(Collectors.toSet());
    ParameterizedTypeReference<Result<Map<String, ManageSwaggerInfoModel>>> reference =
        new ParameterizedTypeReference<>() {};
    WebClient client = webClient.build();
    client
        .mutate()
        .build()
        .get()
        .uri(
            HTTP
                + ServiceConstant.SYSTEM_SERVICE
                + ServiceConstant.SYSTEM_SERVICE_PATH
                + "/manage-service/select/swagger-info")
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(response -> response.bodyToMono(reference))
        .onErrorStop()
        .filter(result -> result.isSuccess() && CollUtil.isNotEmpty(result.getData()))
        .map(Result::getData)
        .doOnNext(
            swaggerInfoData ->
                otherServices.forEach(
                    v -> {
                      List<ServiceInstance> instances = disClient.getInstances(v);
                      if (CollUtil.isNotEmpty(instances)) {
                        instances.forEach(
                            sv -> {
                              String serviceId = sv.getServiceId();
                              ManageSwaggerInfoModel manageSwaggerInfoModel =
                                  swaggerInfoData.get(serviceId);
                              if (Objects.nonNull(manageSwaggerInfoModel)) {
                                Map<String, String> metadata = sv.getMetadata();
                                String contextPath = metadata.get(MANAGEMENT_CONTEXT_PATH);
                                contextPath = StringUtils.isBlank(contextPath) ? "" : contextPath;
                                contextPath = contextPath.replace(ACTUATOR, "");
                                client
                                    .mutate()
                                    .build()
                                    .get()
                                    .uri(
                                        HTTP
                                            + serviceId
                                            + contextPath
                                            + swaggerConfig
                                            + DEFAULT_PATH_SEPARATOR
                                            + SWAGGGER_CONFIG_FILE)
                                    .exchangeToMono(
                                        response -> response.bodyToMono(SwaggerConfigModel.class))
                                    .onErrorStop()
                                    .filter(body -> CollUtil.isNotEmpty(body.getUrls()))
                                    .map(SwaggerConfigModel::getUrls)
                                    .doOnNext(
                                        urlInfos ->
                                            urlInfos.forEach(
                                                urlInfo -> {
                                                  SwaggerUrl otherSwaggerUrl = new SwaggerUrl();
                                                  otherSwaggerUrl.setUrl(
                                                      otherApiDocsPrefix + urlInfo.getUrl());
                                                  otherSwaggerUrl.setDisplayName(
                                                      serviceId + "(" + urlInfo.getName() + ")");
                                                  otherSwaggerUrl.setName(
                                                      serviceId + "(" + urlInfo.getName() + ")");
                                                  apiDocInfoUrlModel.put(
                                                      otherSwaggerUrl.getName(), otherSwaggerUrl);
                                                }))
                                    .subscribe();
                              }
                            });
                      }
                    }))
        .subscribe();
    Set<SwaggerUrl> urls = new HashSet<>(apiDocInfoUrlModel.size());
    apiDocInfoUrlModel.forEach((k, v) -> urls.add(v));
    return urls;
  }
}
