

package com.anyilanxin.anyicloud.corecommon.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.anyilanxin.anyicloud.corecommon.validation.validator.NotBlankOrNullValidator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 空或者null判断
 *
 * @author zxh
 * @date 2019-06-18 10:43
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(NotBlankOrNull.List.class)
@Constraint(validatedBy = {NotBlankOrNullValidator.class})
public @interface NotBlankOrNull {
    // 默认错误消息
    String message() default "内容不能为空";


    // 分组
    Class<?>[] groups() default {};


    // 负载
    Class<? extends Payload>[] payload() default {};

    // 指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotBlankOrNull[] value();
    }
}
