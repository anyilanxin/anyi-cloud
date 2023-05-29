

package com.anyilanxin.anyicloud.auth.oauth2.validate.impl;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckDto;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckModel;
import com.anyilanxin.anyicloud.auth.oauth2.validate.IValidate;
import com.anyilanxin.anyicloud.auth.oauth2.validate.ValidateDto;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 短信验证码实现
 *
 * @author zxh
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
