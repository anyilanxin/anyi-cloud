// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译具体属性
 *
 * @author zxiaozhou
 * @date 2020-06-22 17:27
 * @since JDK11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictField {
    String SUFFIX = "_text";

    /**
     * 字典编码
     *
     * @return String ${@link String} 字典编码
     * @author zxiaozhou
     * @date 2020-06-22 17:26
     */
    String dicCode();


    /**
     * 拼接后缀,默认字段后边添加"_text",自定义后缀请避免属性重复
     *
     * @return String ${@link String} 拼接后缀
     * @author zxiaozhou
     * @date 2020-06-22 17:26
     */
    String dicText() default SUFFIX;
}
