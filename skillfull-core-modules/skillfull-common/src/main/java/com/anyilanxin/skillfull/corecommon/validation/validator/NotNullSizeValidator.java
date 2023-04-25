package com.anyilanxin.skillfull.corecommon.validation.validator;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * 空或者数量判断(用于list)
 *
 * @author zxiaozhou
 * @date 2019-06-18 10:44
 * @since JDK11
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
