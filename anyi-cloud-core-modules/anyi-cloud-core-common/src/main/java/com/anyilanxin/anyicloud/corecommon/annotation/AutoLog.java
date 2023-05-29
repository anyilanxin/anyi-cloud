

package com.anyilanxin.anyicloud.corecommon.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author zxh
 * @date 2020-06-22 17:39
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    /**
     * 查询
     */
    int QUERY = 1;
    /**
     * 添加
     */
    int ADD = 2;
    /**
     * 修改
     */
    int EDIT = 3;
    /**
     * 删除
     */
    int DELETE = 4;
    /**
     * 登录
     */
    int LOGIN = 5;
    /**
     * 退出
     */
    int LOGIN_OUT = 6;
    /**
     * 其他
     */
    int OTHER = 7;

    /**
     * 日志注释
     *
     * @return 日志信息
     */
    String note() default "";


    /**
     * 操作类型
     *
     * @return （1查询，2添加，3修改，4删除,5其他）
     */
    int type() default OTHER;
}
