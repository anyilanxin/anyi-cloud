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

package com.anyilanxin.anyicloud.gateway.filter.partial.post;

import static com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant.PARAM_ENABLED_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

import com.anyilanxin.anyicloud.gateway.utils.CorsWebUtils;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 跨域处理
 *
 * @author zxh
 * @date 2021-08-19 09:12
 * @since 1.0.0
 */
@Slf4j
public class CorsWebGatewayFilterFactory extends AbstractGatewayFilterFactory<CorsWebGatewayFilterFactory.Config> {

    public CorsWebGatewayFilterFactory() {
        super(CorsWebGatewayFilterFactory.Config.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_ENABLED_KEY);
    }


    @Override
    public GatewayFilter apply(CorsWebGatewayFilterFactory.Config config) {
        CorsWebGatewayFilterFactory.CorsWebGatewayFilter gatewayFilter = new CorsWebGatewayFilterFactory.CorsWebGatewayFilter(config);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }

    private static class CorsWebGatewayFilter implements GatewayFilter, Ordered {

        private final CorsWebGatewayFilterFactory.Config config;

        private GatewayFilterFactory<CorsWebGatewayFilterFactory.Config> gatewayFilterFactory;

        public CorsWebGatewayFilter(CorsWebGatewayFilterFactory.Config config) {
            this.config = config;
        }


        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------CorsWebGatewayFilter------------>filter:{}", "跨域处理过滤器");
            return CorsWebUtils.corsHandle(chain, exchange);
        }


        @Override
        public int getOrder() {
            return 1;
        }


        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj).append("New content type", " config.getNewContentType()").append("In class", " config.getInClass()").append("Out class", "config.getOutClass()").toString();
        }


        public void setFactory(GatewayFilterFactory<CorsWebGatewayFilterFactory.Config> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {
        }


        public boolean isEnabled() {
            return enabled;
        }


        public CorsWebGatewayFilterFactory.Config setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
    }
}