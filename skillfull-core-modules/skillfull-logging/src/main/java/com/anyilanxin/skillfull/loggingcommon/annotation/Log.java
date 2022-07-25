// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.loggingcommon.annotation;

import com.anyilanxin.skillfull.loggingcommon.constant.impl.LogBusinessType;
import com.anyilanxin.skillfull.loggingcommon.constant.impl.LogEndpointType;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author zxiaozhou
 * @date 2022-04-04 17:59
 * @since JDK1.8
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
     * 功能
     */
    LogBusinessType businessType() default LogBusinessType.OTHER;

    /**
     * 操作人类别
     */
    LogEndpointType operatorType() default LogEndpointType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;
}
