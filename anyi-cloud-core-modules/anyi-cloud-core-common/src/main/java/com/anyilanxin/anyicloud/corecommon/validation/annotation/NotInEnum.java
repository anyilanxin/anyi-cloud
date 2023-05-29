

package com.anyilanxin.anyicloud.corecommon.validation.annotation;

/**
 * 枚举校验
 *
 * @author zxh
 * @date 2021-07-11 10:51
 * @since 1.0.0
 */

import com.anyilanxin.anyicloud.corecommon.validation.validator.NotInEnumValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotInEnumValidator.class)
public @interface NotInEnum {
    /**
     * 消息
     */
    String message() default "当前类型错误";


    /**
     * 是否自动消息，如果是从枚举中提取
     */
    boolean autoMessage() default false;


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};


    /**
     * 校验的枚举
     */
    Class<? extends Enum<?>> enumClass();


    /**
     * 枚举调用获取方法
     */
    String enumMethod() default "isHaveByType";


    /**
     * 自动消息获取方法
     */
    String messageMethod() default "getAllType";
}
