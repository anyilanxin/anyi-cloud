

package com.anyilanxin.anyicloud.corecommon.validation.validator;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 空或者null判断
 *
 * @author zxh
 * @date 2019-06-18 10:44
 * @since 1.0.0
 */
public class PathNotBlankOrNullValidator implements ConstraintValidator<PathNotBlankOrNull, Object> {
    private static final String PATH_NULL_VALUE = "undefined";

    @Override
    public void initialize(PathNotBlankOrNull constraintAnnotation) {
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        if (value instanceof String) {
            String strValue = (String) value;
            return !PATH_NULL_VALUE.equalsIgnoreCase(strValue);
        }
        return true;
    }
}
