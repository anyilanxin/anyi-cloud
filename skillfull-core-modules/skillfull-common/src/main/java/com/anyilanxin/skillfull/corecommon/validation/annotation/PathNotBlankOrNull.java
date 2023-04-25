package com.anyilanxin.skillfull.corecommon.validation.annotation;


import com.anyilanxin.skillfull.corecommon.validation.validator.PathNotBlankOrNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 针对PathVariable参数验证(此时如果不传会默认为undefined)
 *
 * @author zxiaozhou
 * @date 2019-06-18 10:43
 * @since JDK11
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(PathNotBlankOrNull.List.class)
@Constraint(validatedBy = {PathNotBlankOrNullValidator.class})
public @interface PathNotBlankOrNull {
    //默认错误消息
    String message() default "内容不能为空";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PathNotBlankOrNull[] value();
    }
}
