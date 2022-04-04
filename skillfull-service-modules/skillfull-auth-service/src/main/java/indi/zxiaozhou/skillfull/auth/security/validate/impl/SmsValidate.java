// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.validate.impl;

import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.auth.security.config.properties.SecurityProperties;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckDto;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckModel;
import indi.zxiaozhou.skillfull.auth.security.validate.IValidate;
import indi.zxiaozhou.skillfull.auth.security.validate.ValidateDto;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.SMS_CODE_KEY_PREFIX;

/**
 * 短信验证码实现
 *
 * @author zxiaozhou
 * @date 2020-06-29 02:30
 * @since JDK11
 */
@Component
@RequiredArgsConstructor
public class SmsValidate implements IValidate {
    private final SecurityProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;
    public static final String PHONE = "phone";

    @Override
    public ValidateDto getVerification(JSONObject parameter, HttpServletRequest request) {
        String code = CoreCommonUtils.get32UUId();
        redisTemplate.opsForValue().set(SMS_CODE_KEY_PREFIX + parameter.getString(PHONE), code, properties.getCodeValidityInSeconds());
        // 发送短信验证码
        return null;
    }

    @Override
    public CheckDto checkVerification(CheckModel parameter) {
        String codeKey = SMS_CODE_KEY_PREFIX + parameter.getCodeId();
        Object data = redisTemplate.opsForValue().get(codeKey);
        CheckDto checkDto = new CheckDto();
        if (Objects.nonNull(data)) {
            String code = data.toString();
            if (code.equals(parameter.getCodeValue())) {
                checkDto.setResult(true);
            } else {
                checkDto.setMsg("验证码不正确");
            }
        } else {
            checkDto.setMsg("验证码过期");
        }
        return checkDto;
    }
}
