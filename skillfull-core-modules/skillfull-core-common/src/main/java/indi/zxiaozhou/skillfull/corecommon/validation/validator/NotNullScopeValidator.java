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


import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullScope;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 空或者范围判断(用于有范围的数字)
 *
 * @author zxiaozhou
 * @date 2019-06-18 10:44
 * @since JDK11
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
