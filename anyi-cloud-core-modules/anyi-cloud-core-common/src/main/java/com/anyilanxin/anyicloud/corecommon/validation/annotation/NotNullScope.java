

package com.anyilanxin.anyicloud.corecommon.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.anyilanxin.anyicloud.corecommon.validation.validator.NotNullScopeValidator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 空或者范围判断(用于有范围的数字)
 *
 * @author zxh
 * @date 2019-06-18 10:43
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(NotNullScope.List.class)
@Constraint(validatedBy = {NotNullScopeValidator.class})
public @interface NotNullScope {
    // 默认错误消息
    String message() default "属性不能为空或内容超出范围";


    // 分组
    Class<?>[] groups() default {};


    // 负载
    Class<? extends Payload>[] payload() default {};


    // 最小值
    long min() default 0;


    // 最大值
    long max();

    // 指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNullScope[] value();
    }
}
