

package com.anyilanxin.anyicloud.loggingcommon.annotation;

import com.anyilanxin.anyicloud.loggingcommon.constant.impl.OperateType;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author zxh
 * @date 2022-04-04 17:59
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";


    /**
     * 操作类型
     */
    OperateType businessType() default OperateType.OTHER;


    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;


    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;
}
