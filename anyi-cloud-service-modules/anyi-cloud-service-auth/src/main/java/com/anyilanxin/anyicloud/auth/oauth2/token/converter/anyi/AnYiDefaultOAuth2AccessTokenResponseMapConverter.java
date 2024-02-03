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

package com.anyilanxin.anyicloud.auth.oauth2.token.converter.anyi;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.anyicloud.oauth2common.enums.AnYiOauth2ParamterNames;
import com.anyilanxin.anyicloud.oauth2common.enums.DataFormatType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Converter} that converts the provided {@link OAuth2AccessTokenResponse} to a
 * {@code Map} representation of the OAuth 2.0 Access Token Response parameters.
 *
 * @author Steve Riesenberg
 * @since 5.6
 */
public final class AnYiDefaultOAuth2AccessTokenResponseMapConverter implements Converter<OAuth2AccessTokenResponse, Map<String, Object>> {

    @Override
    public Map<String, Object> convert(OAuth2AccessTokenResponse tokenResponse) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(OAuth2ParameterNames.ACCESS_TOKEN, tokenResponse.getAccessToken().getTokenValue());
        parameters.put(AnYiOauth2ParamterNames.ACCESS_TOKEN_QUERY_NAME.getKey(), AnYiOauth2ParamterNames.ACCESS_TOKEN_QUERY_NAME.getValue());
        parameters.put(AnYiOauth2ParamterNames.BEARER_TOKEN_HEADER_NAME.getKey(), AnYiOauth2ParamterNames.BEARER_TOKEN_HEADER_NAME.getValue());
        parameters.put(OAuth2ParameterNames.TOKEN_TYPE, tokenResponse.getAccessToken().getTokenType().getValue());
        parameters.put(OAuth2ParameterNames.EXPIRES_IN, getExpiresIn(tokenResponse));
        if (!CollectionUtils.isEmpty(tokenResponse.getAccessToken().getScopes())) {
            parameters.put(OAuth2ParameterNames.SCOPE,
                    StringUtils.collectionToDelimitedString(tokenResponse.getAccessToken().getScopes(), " "));
        }
        if (tokenResponse.getRefreshToken() != null) {
            parameters.put(OAuth2ParameterNames.REFRESH_TOKEN, tokenResponse.getRefreshToken().getTokenValue());
        }
        if (!CollectionUtils.isEmpty(tokenResponse.getAdditionalParameters())) {
            parameters.putAll(tokenResponse.getAdditionalParameters());
        }
        DataFormatType type = (DataFormatType) parameters.remove(AnYiSecurityConstants.DATA_FORMAT);
        if (type == DataFormatType.ANYI) {
            Map<String, Object> result = new HashMap<>(5);
            result.put(AnYiResult.DATA_KEY, parameters);
            result.put(AnYiResult.CODE_KEY, AnYiResultStatus.SUCCESS.getCode());
            result.put(AnYiResult.SUCCESS_KEY, true);
            result.put(AnYiResult.MESSAGE_KEY, "获取授权成功");
            result.put(AnYiResult.TIMESTAMP_KEY, System.currentTimeMillis());
            parameters = result;
        }
        return parameters;
    }

    private static long getExpiresIn(OAuth2AccessTokenResponse tokenResponse) {
        if (tokenResponse.getAccessToken().getExpiresAt() != null) {
            return ChronoUnit.SECONDS.between(Instant.now(), tokenResponse.getAccessToken().getExpiresAt());
        }
        return -1;
    }

}
