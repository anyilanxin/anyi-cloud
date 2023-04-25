package com.anyilanxin.skillfull.corecommon.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 类型名称
 *
 * @author zxiaozhou
 * @date 2020-09-28 10:22
 * @since JDK11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ConstantType {
}
