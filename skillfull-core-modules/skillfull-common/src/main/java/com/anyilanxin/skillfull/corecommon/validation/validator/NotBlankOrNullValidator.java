package com.anyilanxin.skillfull.corecommon.validation.validator;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
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
