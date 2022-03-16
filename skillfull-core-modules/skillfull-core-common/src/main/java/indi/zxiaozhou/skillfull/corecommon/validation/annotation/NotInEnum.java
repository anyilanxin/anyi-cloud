package indi.zxiaozhou.skillfull.corecommon.validation.annotation;

/**
 * 枚举校验
 *
 * @author zxiaozhou
 * @date 2021-07-11 10:51
 * @since JDK1.8
 */

import indi.zxiaozhou.skillfull.corecommon.validation.validator.NotInEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotInEnumValidator.class)
public @interface NotInEnum {
    String message() default "当前类型错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String enumMethod() default "isHaveByType";
}
