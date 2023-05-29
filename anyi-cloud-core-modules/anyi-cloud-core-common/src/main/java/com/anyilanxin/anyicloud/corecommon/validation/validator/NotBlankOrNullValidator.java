

package com.anyilanxin.anyicloud.corecommon.validation.validator;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * 空或者null判断
 *
 * @author zxh
 * @date 2019-06-18 10:44
 * @since 1.0.0
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
