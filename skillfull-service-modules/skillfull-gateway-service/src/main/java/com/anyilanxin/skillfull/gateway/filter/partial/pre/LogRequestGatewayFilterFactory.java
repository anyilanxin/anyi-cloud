/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.gateway.filter.partial.pre;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import com.anyilanxin.skillfull.gateway.utils.GatewayCommonUtils;
import com.anyilanxin.skillfull.gateway.utils.LogRecordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 日志记录过滤器(前置)
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Slf4j
public class LogRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<LogRequestGatewayFilterFactory.Config> {

    private final List<HttpMessageReader<?>> messageReaders;

    public LogRequestGatewayFilterFactory() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_SPECIAL_URL_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        LogGatewayFilter gatewayFilter = new LogGatewayFilter(config, this.messageReaders);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }


    private static class LogGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        private final List<HttpMessageReader<?>> messageReaders;

        private GatewayFilterFactory<Config> gatewayFilterFactory;

        public LogGatewayFilter(Config config, List<HttpMessageReader<?>> messageReaders) {
            this.config = config;
            this.messageReaders = messageReaders;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------LogGatewayFilter------------>filter:{}", "日志记录过滤器");
            // 验证特殊url
            GatewayCommonUtils.CheckUrlInfo haveUrl = GatewayCommonUtils.isHaveUrl(exchange, config.specialUrl);
            // 没有url没有任何类型直接运行获取日志
            if (haveUrl.getSpecialUrlType() == 0) {
                return LogRecordUtils.getRequestInfo(exchange, chain, messageReaders);
            }
            // 如果是白名单并且检测成功，这不获取日志
            else if (haveUrl.getSpecialUrlType() == 1 && haveUrl.isResult()) {
                return chain.filter(exchange);
            }
            // 如果是黑名单，并且成功则运行获取日志
            else if (haveUrl.getSpecialUrlType() == 2 && haveUrl.isResult()) {
                return LogRecordUtils.getRequestInfo(exchange, chain, messageReaders);
            }
            // 其余全部读取日志
            return LogRecordUtils.getRequestInfo(exchange, chain, messageReaders);
        }


        @Override
        /** 必须大于LOAD_BALANCER_CLIENT_FILTER_ORDER(10150)，即负载均衡过滤器(ReactiveLoadBalancerClientFilter)的order,否则拿不到真实目标服务ip(request数据) */
        public int getOrder() {
            return LOAD_BALANCER_CLIENT_FILTER_ORDER + 1;
        }

        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj)
                    .append("New content type", " config.getNewContentType()")
                    .append("In class", " config.getInClass()")
                    .append("Out class", "config.getOutClass()").toString();
        }

        public void setFactory(GatewayFilterFactory<Config> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }
    }


    @Validated
    public static class Config {
        /**
         * 特殊url
         */
        private RouteMetaSpecialUrlModel specialUrl;

        public RouteMetaSpecialUrlModel getSpecialUrl() {
            return specialUrl;
        }

        public Config setSpecialUrl(String specialUrl) {
            if (StringUtils.isNotBlank(specialUrl)) {
                this.specialUrl = JSONObject.parseObject(specialUrl, RouteMetaSpecialUrlModel.class);
            }
            return this;
        }
    }
}
