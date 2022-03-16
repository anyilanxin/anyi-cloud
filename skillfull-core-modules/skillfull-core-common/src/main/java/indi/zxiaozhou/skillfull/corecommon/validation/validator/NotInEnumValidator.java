package indi.zxiaozhou.skillfull.corecommon.validation.validator;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotInEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * 判断是否是某个枚举值
 *
 * @author zxiaozhou
 * @date 2021-07-11 10:52
 * @since JDK1.8
 */
public class NotInEnumValidator implements ConstraintValidator<NotInEnum, Object> {
    private Class<? extends Enum<?>> enumClass;
    private String enumMethod;

    @Override
    public void initialize(NotInEnum notInEnum) {
        enumMethod = notInEnum.enumMethod();
        enumClass = notInEnum.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return Boolean.TRUE;
        }
        if (Objects.isNull(enumClass) || Objects.isNull(enumMethod)) {
            return Boolean.TRUE;
        }
        Class<?> valueClass = value.getClass();
        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new RuntimeException(String.format("%s 方法返回值不是boolean类型 %s class", enumMethod, enumClass));
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException(String.format("%s 当前指定枚举校验方法不是静态方法 %s class", enumMethod, enumClass));
            }
            Boolean result = (Boolean) method.invoke(null, value);
            return result != null && result;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(String.format("This %s(%s) 方法不存在 %s", enumMethod, valueClass, enumClass), e);
        }
    }

}

