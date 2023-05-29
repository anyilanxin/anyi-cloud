

package com.anyilanxin.anyicloud.corecommon.validation.validator;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullScope;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 空或者范围判断(用于有范围的数字)
 *
 * @author zxh
 * @date 2019-06-18 10:44
 * @since 1.0.0
 */
public class NotNullScopeValidator implements ConstraintValidator<NotNullScope, Integer> {
    private long min;
    private long max;

    @Override
    public void initialize(NotNullScope constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        return value >= min && value <= max;
    }
}
