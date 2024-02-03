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
import com.anyilanxin.anyicloud.oauth2common.enums.DataFormatType;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权错误信息转化
 *
 * @author zxh
 * @date 2024-01-23 15:55
 * @since 1.0.0
 */
public class AnYiOAuth2ErrorParametersConverter implements Converter<OAuth2Error, Map<String, String>> {

    @Override
    public Map<String, String> convert(@NotNull OAuth2Error oauth2Error) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("error", oauth2Error.getErrorCode());
        if (StringUtils.hasText(oauth2Error.getDescription())) {
            parameters.put("error_description", oauth2Error.getDescription());
        }
        if (StringUtils.hasText(oauth2Error.getUri())) {
            parameters.put("error_uri", oauth2Error.getUri());
        }
        if (oauth2Error instanceof AnYiOAuth2Error anYiOAuth2Error) {
            Map<String, Object> additionalParameters = anYiOAuth2Error.getAdditionalParameters();
            DataFormatType type = (DataFormatType) additionalParameters.remove(AnYiSecurityConstants.DATA_FORMAT);
            if (type == DataFormatType.ANYI) {
                Map<String, String> result = new HashMap<>(5);
                result.put(AnYiResult.CODE_KEY, String.valueOf(AnYiResultStatus.ACCESS_ERROR.getCode()));
                result.put(AnYiResult.SUCCESS_KEY, String.valueOf(false));
                result.put(AnYiResult.MESSAGE_KEY, parameters.get("error"));
                result.put(AnYiResult.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
                parameters = result;
            }
        }
        return parameters;
    }
}
