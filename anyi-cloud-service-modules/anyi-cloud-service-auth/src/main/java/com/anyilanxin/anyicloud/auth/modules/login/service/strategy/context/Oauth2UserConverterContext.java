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
package com.anyilanxin.anyicloud.auth.modules.login.service.strategy.context;


import com.anyilanxin.anyicloud.auth.modules.login.model.security.BasicOAuth2User;
import com.anyilanxin.anyicloud.auth.modules.login.service.strategy.Oauth2UserConverterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * 三方oauth2登录获取的用户信息转换处理
 *
 * @author vains
 */
@Component
@RequiredArgsConstructor
public class Oauth2UserConverterContext {

    /**
     * 注入所有实例，map的key是实例在ioc中的名字
     * 这里通过构造器注入所有Oauth2UserConverterStrategy的实例，实例名之前在编写时已经通过
     * {@link Component }注解指定过bean的名字，可以根据给定bean名称从map中获取对应的实例(如果存在)
     */
    private final Map<String, Oauth2UserConverterStrategy> userConverterStrategyMap;

    /**
     * 获取转换器实例
     *
     * @param loginType 三方登录方式
     * @return 转换器实例 {@link Oauth2UserConverterStrategy}
     */
    public Oauth2UserConverterStrategy getInstance(String loginType) {
        if (ObjectUtils.isEmpty(loginType)) {
            throw new UnsupportedOperationException("登录方式不能为空.");
        }
        Oauth2UserConverterStrategy userConverterStrategy = userConverterStrategyMap.get(loginType);
        if (userConverterStrategy == null) {
            throw new UnsupportedOperationException("不支持[" + loginType + "]登录方式获取用户信息转换器");
        }
        return userConverterStrategy;
    }

    /**
     * 根据登录方式获取转换器实例，使用转换器获取用户信息
     *
     * @param userRequest 获取三方用户信息入参
     * @param oAuth2User  三方登录获取到的认证信息
     * @return {@link BasicOAuth2User}
     */
    public BasicOAuth2User convert(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // 获取三方登录配置的registrationId，这里将他当做登录方式
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 获取三方账号字段
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        // 转换用户信息
        BasicOAuth2User basicOauth2User = this.getInstance(registrationId).convert(oAuth2User);
        // 设置三方账号字段
        basicOauth2User.setNameAttributeKey(userNameAttributeName);
        // 获取AccessToken
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        // 设置token
        basicOauth2User.setCredentials(accessToken.getTokenValue());
        // 设置账号的方式
        basicOauth2User.setType(registrationId);
        Instant expiresAt = accessToken.getExpiresAt();
        if (expiresAt != null) {
            LocalDateTime tokenExpiresAt = expiresAt.atZone(ZoneId.of("UTC")).toLocalDateTime();
            // token过期时间
            basicOauth2User.setCredentialsExpiresAt(tokenExpiresAt);
        }
        return basicOauth2User;
    }

}
