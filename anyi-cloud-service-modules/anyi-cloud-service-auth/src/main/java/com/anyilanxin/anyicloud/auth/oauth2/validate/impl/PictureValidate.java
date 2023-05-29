

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
