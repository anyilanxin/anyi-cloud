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
package com.anyilanxin.anyicloud.auth.oauth2.validate.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.auth.core.constant.AuthCommonConstant;
import com.anyilanxin.anyicloud.auth.core.properties.AuthProperty;
import com.anyilanxin.anyicloud.auth.oauth2.validate.*;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.auth.oauth2.validate.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 图片验证码实现
 *
 * @author zxh
 * @date 2020-06-29 02:30
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PictureValidate implements IValidate {
    private final AuthProperty authProperty;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public ValidateDto getVerification(JSONObject parameter, HttpServletRequest request) {
        // 生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(300, 150, 4, 20);
        String imageBase64 = captcha.getImageBase64Data();
        String code = captcha.getCode();
        String codeId = CoreCommonUtils.get32UUId();
        // 存储
        redisTemplate.opsForValue().set(AuthCommonConstant.PICTURE_CODE_KEY_PREFIX + codeId, code, authProperty.getCodePictureSeconds(), TimeUnit.SECONDS);
        // 构造验证码web端信息
        ValidateDto validateDto = new ValidateDto();
        validateDto.setCodeType(CodeType.PICTURE_CODE);
        validateDto.setValidTime(authProperty.getCodePictureSeconds());
        validateDto.setCodeValue(imageBase64);
        validateDto.setCodeId(codeId);
        validateDto.setStatus(true);
        return validateDto;
    }


    @Override
    public CheckDto checkVerification(CheckModel parameter) {
        CheckDto checkDto = new CheckDto();
        Object data = redisTemplate.opsForValue().get(AuthCommonConstant.PICTURE_CODE_KEY_PREFIX + parameter.getCodeId());
        redisTemplate.delete(AuthCommonConstant.PICTURE_CODE_KEY_PREFIX + parameter.getCodeId());
        if (Objects.nonNull(data)) {
            String code = data.toString();
            if (code.equalsIgnoreCase(parameter.getCodeValue())) {
                checkDto.setResult(true);
            } else {
                checkDto.setMsg("验证码错误");
            }
        } else {
            checkDto.setMsg("验证码过期");
        }
        return checkDto;
    }
}
