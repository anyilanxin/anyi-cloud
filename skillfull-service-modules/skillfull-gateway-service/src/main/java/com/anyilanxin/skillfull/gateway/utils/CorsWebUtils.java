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

package com.anyilanxin.skillfull.gateway.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域处理工具类
 *
 * @author zxiaozhou
 * @date 2021-08-19 09:15
 * @since JDK1.8
 */
@Slf4j
public class CorsWebUtils {

    /**
     * 跨域处理工具(OPTIONS请求)
     *
     * @param exchange ${@link ServerWebExchange}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxiaozhou
     * @date 2021-07-14 22:22
     */
    public static Mono<Void> corsHandle(WebFilterChain chain, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            addCorsHeaders(exchange);
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 跨域处理工具(非OPTIONS请求)
     *
     * @param exchange ${@link ServerWebExchange}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxiaozhou
     * @date 2021-07-14 22:22
     */
    public static Mono<Void> corsHandle(GatewayFilterChain chain, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            addCorsHeaders(exchange);
        }
        return chain.filter(exchange);
    }

    public static void addCorsHeaders(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders requestHeaders = request.getHeaders();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.setAccessControlAllowOrigin("*");
        if (CollUtil.isNotEmpty(requestHeaders.getAccessControlRequestHeaders())) {
            headers.setAccessControlAllowHeaders(requestHeaders.getAccessControlRequestHeaders());
        }
        if (CollUtil.isNotEmpty(requestHeaders.getAccessControlAllowMethods())) {
            headers.setAccessControlAllowMethods(requestHeaders.getAccessControlAllowMethods());
        }
        headers.setAccessControlAllowCredentials(true);
        if (CollUtil.isNotEmpty(requestHeaders.getAccessControlExposeHeaders())) {
            headers.setAccessControlExposeHeaders(requestHeaders.getAccessControlExposeHeaders());
        }
        headers.setAccessControlMaxAge(18000L);
    }
}
