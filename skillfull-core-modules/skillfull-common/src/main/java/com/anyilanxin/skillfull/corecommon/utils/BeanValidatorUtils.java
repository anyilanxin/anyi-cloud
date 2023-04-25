/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
