

package com.anyilanxin.anyicloud.corecommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译具体属性
 *
 * @author zxh
 * @date 2020-06-22 17:27
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictField {
    String SUFFIX = "_text";

    /**
     * 字典编码
     *
     * @return String ${@link String} 字典编码
     * @author zxh
     * @date 2020-06-22 17:26
     */
    String dicCode();


    /**
     * 拼接后缀,默认字段后边添加"_text",自定义后缀请避免属性重复
     *
     * @return String ${@link String} 拼接后缀
     * @author zxh
     * @date 2020-06-22 17:26
     */
    String dicText() default SUFFIX;
}
