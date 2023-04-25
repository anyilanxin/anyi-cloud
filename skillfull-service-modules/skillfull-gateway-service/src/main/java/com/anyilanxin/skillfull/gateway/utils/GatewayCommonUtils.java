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
package com.anyilanxin.skillfull.gateway.utils;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import com.anyilanxin.skillfull.corecommon.model.system.SpecialUrlModel;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import io.netty.buffer.UnpooledByteBufAllocator;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
* 网关公共工具
*
* @author zxiaozhou
* @date 2021-05-06 12:54
* @since JDK1.8
*/
@Component
@Slf4j
@RequiredArgsConstructor
public class GatewayCommonUtils {
    private static GatewayCommonUtils utils;
    private final AntPathMatcher antPathMatcher;

    /**
    * 创建response
    *
    * @param exchange content
    * @param statusCode statusCode
    * @param message message
    */
    public static Mono<Void> responseWrite(
            ServerWebExchange exchange,
            HttpStatus statusCode,
            Status status,
            String message,
            Map<String, String> headers) {
        if (CollUtil.isNotEmpty(headers)) {
            headers.forEach((key, value) -> exchange.getResponse().getHeaders().add(key, value));
        }
        exchange.getResponse().setStatusCode(statusCode);
        if (CollectionUtil.isEmpty(exchange.getResponse().getHeaders().get("content-type"))) {
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        }
        CorsWebUtils.addCorsHeaders(exchange);
        if (StringUtils.isNotBlank(message)) {
            return exchange
                    .getResponse()
                    .writeWith(
                            Flux.create(
                                    sink -> {
                                        Result<String> result = new Result<>();
                                        result.setSuccess(false);
                                        result.setCode(status.getCode());
                                        result.setTimestamp(System.currentTimeMillis());
                                        result.setMessage(message);
                                        NettyDataBufferFactory nettyDataBufferFactory =
                                                new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                                        DataBuffer dataBuffer =
                                                nettyDataBufferFactory.wrap(
                                                        JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8));
                                        sink.next(dataBuffer);
                                        sink.complete();
                                    }));
        } else {
            return exchange.getResponse().setComplete();
        }
    }

    /**
    * 检测当前url是否在提供的名单中
    *
    * @param exchange ${@link ServerWebExchange} 请求信息
    * @param specialUrl ${@link RouteMetaSpecialUrlModel} 配置信息
    * @return CheckUrlInfo 检测结果 ${@link CheckUrlInfo}
    * @author zxiaozhou
    * @date 2021-07-28 23:15
    */
    public static CheckUrlInfo isHaveUrl(
            ServerWebExchange exchange, RouteMetaSpecialUrlModel specialUrl) {
        CheckUrlInfo result = new CheckUrlInfo();
        //        if (Objects.isNull(specialUrl) ||
        // CollectionUtil.isEmpty(specialUrl.getBlackSpecialUrls())) {
        //            return result;
        //        }
        //        Set<SpecialUrlModel> checkUrlList = specialUrl.getSpecialUrl();
        //        result.setResult(GatewayCommonUtils.isHaveUrl(exchange, checkUrlList));
        //        result.setSpecialUrlType(specialUrl.getSpecialUrlType());
        return result;
    }

    /**
    * 检测当前是否为白名单中的请求
    *
    * @param exchange ${@link ServerWebExchange} 请求信息
    * @param checkUrlList ${@link Set<SpecialUrlModel>} 待检验url信息
    * @return boolean 检测结果
    * @author zxiaozhou
    * @date 2021-07-28 22:50
    */
    public static boolean isHaveUrl(ServerWebExchange exchange, Set<SpecialUrlModel> checkUrlList) {
        if (Objects.isNull(exchange) || CollectionUtil.isEmpty(checkUrlList)) {
            return false;
        }
        URI uriInfo = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        String path = uriInfo.getPath();
        if (StringUtils.isBlank(path)) {
            return false;
        }
        for (SpecialUrlModel whiteInfo : checkUrlList) {
            String pattern = CoreCommonUtils.getUri(whiteInfo.getUrl());
            if (whiteInfo.getLimitMethod() == 1) {
                Set<String> requestMethods = whiteInfo.getRequestMethodSet();
                HttpMethod httpMethod = exchange.getRequest().getMethod();
                if (Objects.isNull(httpMethod) || CollectionUtil.isEmpty(requestMethods)) {
                    continue;
                }
                for (String method : requestMethods) {
                    if (method.matches(method) && utils.antPathMatcher.match(pattern, path)) {
                        return true;
                    }
                }
            } else {
                if (utils.antPathMatcher.match(pattern, path)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class CheckUrlInfo implements Serializable {
        private static final long serialVersionUID = 2300450135302465210L;

        /** 结果:true-存在,false-不存在 */
        private boolean result;

        /** 特殊url类型:1-白名单(放行url),2-黑名单(只处理url),0-未知(未找到url时状态) */
        private int specialUrlType;
    }

    @PostConstruct
    private void init() {
        utils = this;
    }
}
