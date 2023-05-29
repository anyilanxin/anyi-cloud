

package com.anyilanxin.anyicloud.corecommon.validation.validator;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.CollectionUtils;

/**
 * 空或者数量判断(用于list)
 *
 * @author zxh
 * @date 2019-06-18 10:44
 * @since 1.0.0
 */
public class NotNullSizeValidator implements ConstraintValidator<NotNullSize, Collection<?>> {
    private int min;
    private int max;

    @Override
    public void initialize(NotNullSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }


    @Override
    public boolean isValid(Collection value, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isEmpty(value)) {
            return false;
        }
        int size = value.size();
        return size >= min && size <= max;
    }
}
