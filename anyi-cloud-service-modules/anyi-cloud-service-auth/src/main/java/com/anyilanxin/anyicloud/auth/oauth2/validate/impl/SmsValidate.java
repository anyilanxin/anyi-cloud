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

package com.anyilanxin.anyicloud.auth.oauth2.validate.impl;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.auth.oauth2.validate.ValidateDto;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckDto;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckModel;
import com.anyilanxin.anyicloud.auth.oauth2.validate.IValidate;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 短信验证码实现
 *
 * @author 安一老厨
 * @date 2020-06-29 02:30
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SmsValidate implements IValidate {
    // private final SecurityProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;
    public static final String PHONE = "phone";

    @Override
    public ValidateDto getVerification(JSONObject parameter, HttpServletRequest request) {
        String code = CoreCommonUtils.get32UUId();
        // redisTemplate.opsForValue().set(SMS_CODE_KEY_PREFIX +
        // parameter.getString(PHONE),
        // code, properties.getCodeValidityInSeconds());
        // 发送短信验证码
        return null;
    }


    @Override
    public CheckDto checkVerification(CheckModel parameter) {
        // String codeKey = SMS_CODE_KEY_PREFIX + parameter.getCodeId();
        // Object data = redisTemplate.opsForValue().get(codeKey);
        CheckDto checkDto = new CheckDto();
        // if (Objects.nonNull(data)) {
        // String code = data.toString();
        // if (code.equals(parameter.getCodeValue())) {
        // checkDto.setResult(true);
        // } else {
        // checkDto.setMsg("验证码不正确");
        // }
        // } else {
        // checkDto.setMsg("验证码过期");
        // }
        return checkDto;
    }
}
