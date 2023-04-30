/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.validation.validator;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotInEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

/**
 * 判断是否是某个枚举值
 *
 * @author zxh
 * @date 2021-07-11 10:52
 * @since 1.0.0
 */
public class NotInEnumValidator implements ConstraintValidator<NotInEnum, Object> {
    private Class<? extends Enum<?>> enumClass;
    private String enumMethod;

    private boolean autoMessage;

    private String messageMethod;

    private String message;

    @Override
    public void initialize(NotInEnum notInEnum) {
        enumMethod = notInEnum.enumMethod();
        enumClass = notInEnum.enumClass();
        autoMessage = notInEnum.autoMessage();
        messageMethod = notInEnum.messageMethod();
        message = notInEnum.message();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        ConstraintValidatorContextImpl context = null;
        if (constraintValidatorContext instanceof ConstraintValidatorContextImpl) {
            context = (ConstraintValidatorContextImpl) constraintValidatorContext;
        }
        if (Objects.isNull(value)) {
            return Boolean.TRUE;
        }
        if (Objects.isNull(this.enumClass) || Objects.isNull(this.enumMethod)) {
            return Boolean.TRUE;
        }
        Class<?> valueClass = value.getClass();
        try {
            Method method = this.enumClass.getMethod(this.enumMethod, valueClass);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new RuntimeException(String.format("%s 方法返回值不是boolean类型 %s class", this.enumMethod, this.enumClass));
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException(String.format("%s 当前指定枚举校验方法不是静态方法 %s class", this.enumMethod, this.enumClass));
            }
            Boolean result = (Boolean) method.invoke(null, value);
            // 动态解析消息
            if (Objects.nonNull(context) && this.autoMessage) {
                Method messageMethod = this.enumClass.getMethod(this.messageMethod);
                String message = (String) messageMethod.invoke(null);
                context.addMessageParameter(CommonCoreConstant.DYNAMIC_VALIDATE_MESSAGE_KEY, this.message + message);
            }
            return result != null && result;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(String.format("This %s(%s) 方法不存在 %s", enumMethod, valueClass, enumClass), e);
        }
    }
}
