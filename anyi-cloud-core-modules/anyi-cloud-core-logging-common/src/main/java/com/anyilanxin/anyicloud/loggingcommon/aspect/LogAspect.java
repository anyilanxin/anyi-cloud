

package com.anyilanxin.anyicloud.loggingcommon.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author zxh
 * @date 2022-04-04 18:15
 * @since 1.0.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 切点使用注解形式
     *
     * @author zxh
     * @date 2022-04-04 18:00
     */
    @Pointcut("@annotation(com.anyilanxin.anyicloud.loggingcommon.annotation.Log)")
    public void logPointCut() {
    }


    /**
     * 环绕
     *
     * @param point ${@link ProceedingJoinPoint}
     * @return Object ${@link Object}
     * @author zxh
     * @date 2022-04-04 18:00
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = point.proceed();
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            throw e;
        }
        long endTime = System.currentTimeMillis();
        return result;
    }
}
