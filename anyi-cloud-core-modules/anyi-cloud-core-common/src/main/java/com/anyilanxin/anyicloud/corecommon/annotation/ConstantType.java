

package com.anyilanxin.anyicloud.corecommon.annotation;

import java.lang.annotation.*;

import org.springframework.stereotype.Indexed;

/**
 * 类型名称
 *
 * @author zxh
 * @date 2020-09-28 10:22
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ConstantType {
}
