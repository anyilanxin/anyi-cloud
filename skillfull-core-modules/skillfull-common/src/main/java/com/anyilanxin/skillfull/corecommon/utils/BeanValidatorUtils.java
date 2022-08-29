// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.utils;

import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Set;

/**
 * 对象验证器
 *
 * @author TY
 * @date 2020-09-23 16:49
 * @since JDK1.8
 */
public class BeanValidatorUtils {
    /**
     * 验证某个bean的参数
     *
     * @param object 被校验的参数
     * @return ValidateResult ${@link ValidateResult}
     * @throws ValidationException 如果参数校验不成功则抛出此异常
     */
    public static <T> ValidateResult validate(T object) {
        ValidateResult result = new ValidateResult();
        result.setSuccess(true);
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        if (CollUtil.isNotEmpty(constraintViolations)) {
            StringBuffer sb = new StringBuffer();
            constraintViolations.forEach(v -> sb.append(v.getMessage()).append("、"));
            result.setSuccess(false);
            result.setErrorMsg(StringUtils.removeEnd(sb.toString(), "、"));
        }
        return result;
    }


    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode

    @SuperBuilder(toBuilder = true)

    @NoArgsConstructor
    public static class ValidateResult implements Serializable {
        private static final long serialVersionUID = 8409603977835186912L;

        @Schema(name = "success", title = "验证结果:true-成功,false-失败")
        private boolean success;

        @Schema(name = "errorMsg", title = "异常消息")
        private String errorMsg;
    }
}
