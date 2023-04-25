package com.anyilanxin.skillfull.corecommon.validation.validator;

import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;

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
