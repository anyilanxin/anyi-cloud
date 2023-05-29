

package com.anyilanxin.anyicloud.corecommon.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.anyilanxin.anyicloud.corecommon.validation.validator.NotNullSizeValidator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 空或者数量判断(用于list)
 *
 * @author zxh
 * @date 2019-06-18 10:43
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(NotNullSize.List.class)
@Constraint(validatedBy = {NotNullSizeValidator.class})
public @interface NotNullSize {
    // 默认错误消息
    String message() default "属性不能为空";


    // 分组
    Class<?>[] groups() default {};


    // 负载
    Class<? extends Payload>[] payload() default {};


    // 最小数量
    int min() default 1;


    // 最大数量
    int max() default 2147483647;

    // 指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNullSize[] value();
    }
}
