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


package com.anyilanxin.skillfull.auth.oauth2;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.auth.core.constant.AuthCommonConstant;
import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * token增强
 *
 * @author zxiaozhou
 * @date 2022-02-14 03:05
 * @since JDK1.8
 */
@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        if (oAuth2AccessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
            Map<String, Object> additionalInformation = new HashMap<>();
            // 如果非客户端模式则加入端信息
            if (!oAuth2Authentication.isClientOnly()) {
                Map<String, String> requestParameters =
                        oAuth2Authentication.getOAuth2Request().getRequestParameters();
                if (CollUtil.isNotEmpty(requestParameters)) {
                    String endpoint = requestParameters.get(AuthCommonConstant.ENDPOINT_REQUEST_KEY);
                    if (StringUtils.isBlank(endpoint)) {
                        endpoint = AuthCommonConstant.ENDPOINT_DEFAULT;
                    }
                    additionalInformation.put(AuthCommonConstant.ENDPOINT_REQUEST_KEY, endpoint);
                }
            }
            additionalInformation.put("token_query_name", AuthConstant.ACCESS_TOKEN_QUERY_NAME);
            additionalInformation.put("bearer_token_header_name", AuthConstant.BEARER_TOKEN_HEADER_NAME);
            token.setAdditionalInformation(additionalInformation);
            return token;
        }
        return oAuth2AccessToken;
    }
}
