/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.corecommon.utils;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.cache.InMemoryCache;
import com.anyilanxin.anyicloud.corecommon.config.properties.SkillfullOauthProperty;

import java.net.URI;
import java.util.Objects;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 通过一个客户端授权模式的token
 *
 * @author zxh
 * @date 2022-08-10 09:24
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ClientTokenUtils {
    private static final String URL = "http://auth-service/auth/oauth/token?grant_type=%s&client_id=%s&client_secret=%s";
    private static ClientTokenUtils utils;
    private static final String CACHE_ACCESS_TOKEN_KEY = "SKILLFULL_CLIENT_ACCESS_TOKEN";
    private final SkillfullOauthProperty skillfullOauthProperty;
    private final LoadBalancerClient loadBalancer;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 客户端模式获取token
     *
     * @return String
     * @author zxh
     * @date 2022-08-10 09:25
     */
    public static String getTokenToAuthService() {
        if (Objects.nonNull(utils.skillfullOauthProperty) && StringUtils.isNotBlank(utils.skillfullOauthProperty.getClientId()) && StringUtils.isNotBlank(utils.skillfullOauthProperty.getClientSecret())) {
            String accessToken = InMemoryCache.getCache(CACHE_ACCESS_TOKEN_KEY);
            if (StringUtils.isNotBlank(accessToken)) {
                return accessToken;
            }
            String formatUrl = String.format(URL, utils.skillfullOauthProperty.getGrantType().getType(), utils.skillfullOauthProperty.getClientId(), utils.skillfullOauthProperty.getClientSecret());
            URI uri = URI.create(formatUrl);
            ServiceInstance instance = utils.loadBalancer.choose(uri.getHost());
            if (Objects.isNull(instance)) {
                return "";
            }
            formatUrl = formatUrl.replaceFirst(uri.getHost(), instance.getHost() + ":" + instance.getPort());
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> objectResponseEntity = restTemplate.postForEntity(formatUrl, null, JSONObject.class);
            if (objectResponseEntity.getStatusCode() == HttpStatus.OK) {
                JSONObject body = objectResponseEntity.getBody();
                if (Objects.nonNull(body) && !body.isEmpty()) {
                    JSONObject data = body.getJSONObject("data");
                    if (Objects.nonNull(data) && !data.isEmpty()) {
                        accessToken = data.getString("access_token");
                        int expiresIn = data.getIntValue("expires_in");
                        if (expiresIn > 240) {
                            InMemoryCache.addCache(CACHE_ACCESS_TOKEN_KEY, accessToken, expiresIn - 240);
                        }
                        return accessToken;
                    }
                }
            }
        }
        return "";
    }
}
