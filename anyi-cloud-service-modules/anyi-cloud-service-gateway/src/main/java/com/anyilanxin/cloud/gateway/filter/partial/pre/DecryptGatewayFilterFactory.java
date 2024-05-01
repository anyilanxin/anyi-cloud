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

package com.anyilanxin.cloud.gateway.filter.partial.pre;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import com.anyilanxin.cloud.gateway.utils.DecryptEncryptUtils;
import com.anyilanxin.cloud.gateway.utils.GatewayCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.anyilanxin.cloud.corecommon.constant.CoreCommonGatewayConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 加密过滤器(前置)
 *
 * @author zxh
 * @date 2020-09-11 22:34
 * @since 1.0.0
 */
@Slf4j
public class DecryptGatewayFilterFactory extends AbstractGatewayFilterFactory<DecryptGatewayFilterFactory.Config> {

    private final List<HttpMessageReader<?>> messageReaders;

    public DecryptGatewayFilterFactory() {
        super(DecryptGatewayFilterFactory.Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }


    public DecryptGatewayFilterFactory(List<HttpMessageReader<?>> messageReaders) {
        super(DecryptGatewayFilterFactory.Config.class);
        this.messageReaders = messageReaders;
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_SPECIAL_URL_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        EncryptDecryptGatewayFilter gatewayFilter = new EncryptDecryptGatewayFilter(config, this.messageReaders);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }

    private static class EncryptDecryptGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        private final List<HttpMessageReader<?>> messageReaders;

        private GatewayFilterFactory<Config> gatewayFilterFactory;

        public EncryptDecryptGatewayFilter(Config config, List<HttpMessageReader<?>> messageReaders) {
            this.config = config;
            this.messageReaders = messageReaders;
        }


        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------EncryptDecryptGatewayFilter------------>filter:{}", "解密过滤器");
            // 验证特殊url
            GatewayCommonUtils.CheckUrlInfo haveUrl = GatewayCommonUtils.isHaveUrl(exchange, config.getSpecialUrl());
            // 没有url没有任何类型直接运行解密
            if (haveUrl.getSpecialUrlType() == 0) {
                return DecryptEncryptUtils.decrypt(exchange, chain, messageReaders);
            }
            // 如果是白名单并且检测成功，这不解密
            else if (haveUrl.getSpecialUrlType() == 1 && haveUrl.isResult()) {
                return chain.filter(exchange);
            }
            // 如果是黑名单，并且成功则运行解密
            else if (haveUrl.getSpecialUrlType() == 2 && haveUrl.isResult()) {
                return DecryptEncryptUtils.decrypt(exchange, chain, messageReaders);
            }
            // 其余全部解密
            return DecryptEncryptUtils.decrypt(exchange, chain, messageReaders);
        }


        @Override
        public int getOrder() {
            return 4;
        }


        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj).append("New content type", " config.getNewContentType()").append("In class", " config.getInClass()").append("Out class", "config.getOutClass()").toString();
        }


        public void setFactory(GatewayFilterFactory<Config> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }
    }

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
