/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.loggingcommon.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
* 日志切面
*
* @author zxiaozhou
* @date 2022-04-04 18:15
* @since JDK1.8
*/
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
    * 切点使用注解形式
    *
    * @author zxiaozhou
    * @date 2022-04-04 18:00
    */
    @Pointcut("@annotation(com.anyilanxin.skillfull.loggingcommon.annotation.Log)")
    public void logPointCut() {}

    /**
    * 环绕
    *
    * @param point ${@link ProceedingJoinPoint}
    * @return Object ${@link Object}
    * @author zxiaozhou
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
