package com.anyilanxin.skillfull.database.datascope.annotation;

import java.lang.annotation.*;


/**
 * 数据权限过滤注解
 *
 * @author zxiaozhou
 * @date 2022-04-04 18:02
 * @since JDK1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
}
