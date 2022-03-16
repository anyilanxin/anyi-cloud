// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.validation.validator;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 空或者null判断
 *
 * @author zxiaozhou
 * @date 2019-06-18 10:44
 * @since JDK11
 */
public class NotBlankOrNullValidator implements ConstraintValidator<NotBlankOrNull, Object> {
    @Override
    public void initialize(NotBlankOrNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        return !(value instanceof String) || !StringUtils.isBlank(value.toString());
    }
}
