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

import com.anyilanxin.skillfull.gateway.core.config.properties.CustomSecurityProperties;
import com.anyilanxin.skillfull.gateway.filter.partial.post.CorsWebGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.post.LogResponseGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.AuthorizeGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.LogRequestGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.webfilter.CorsOptionsWebFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;

/**
 * 网关过滤器配置
 *
 * @author zxiaozhou
 * @date 2021-06-17 11:58
 * @since JDK1.8
 */
@SpringBootConfiguration
@RequiredArgsConstructor
public class GatewayFilterConfig {

  @Bean
  public CorsOptionsWebFilter corsOptionsWebFilter() {
    return new CorsOptionsWebFilter();
  }

  @Bean
  public AuthorizeGatewayFilterFactory authorizeGatewayFilterFactory(
      final CustomSecurityProperties securityProperties, final AntPathMatcher antPathMatcher) {
    return new AuthorizeGatewayFilterFactory(securityProperties, antPathMatcher);
  }

  @Bean
  public LogRequestGatewayFilterFactory logRequestGatewayFilterFactory() {
    return new LogRequestGatewayFilterFactory();
  }

  @Bean
  public LogResponseGatewayFilterFactory logResponseGatewayFilterFactory() {
    return new LogResponseGatewayFilterFactory();
  }

  @Bean
  public CorsWebGatewayFilterFactory corsWebGatewayFilterFactory() {
    return new CorsWebGatewayFilterFactory();
  }
}
