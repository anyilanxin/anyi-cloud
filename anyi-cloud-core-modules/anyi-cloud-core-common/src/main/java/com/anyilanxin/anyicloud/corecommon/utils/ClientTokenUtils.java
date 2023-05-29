

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
