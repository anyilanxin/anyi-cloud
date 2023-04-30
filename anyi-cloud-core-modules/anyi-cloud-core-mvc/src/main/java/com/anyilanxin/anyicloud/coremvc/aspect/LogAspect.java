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

package com.anyilanxin.anyicloud.coremvc.aspect;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.annotation.AutoLog;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.anyicloud.coremvc.aspect.model.OperateModel;
import com.anyilanxin.anyicloud.coremvc.utils.ServletUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统日志，切面处理类
 *
 * @author 安一老厨
 * @date 2020-08-27 16:59
 * @since 1.0.0
 */
// @Aspect
// @Component
@Slf4j
// @RequiredArgsConstructor
public class LogAspect {
    private static final String REQUEST_JSON_TYPE = "application";

    @Pointcut("@annotation(com.anyilanxin.anyicloud.corecommon.annotation.AutoLog)")
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 执行方法
        try {
            Object result = point.proceed();
            saveSysLog(point, result);
            return result;
        } catch (Exception e) {
            log.error("------------LogAspect------------>around--->\n异常消息:{}", e.getMessage());
            saveSysLog(point, e);
            throw e;
        }
    }


    private void saveSysLog(ProceedingJoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        OperateModel model = new OperateModel();
        model.setFileName(className);
        model.setMethodName(methodName);
        model.setLogNote(autoLog.note());
        model.setOperateType(autoLog.type());
        model.setMethodParams(getMethodParams(signature, joinPoint));
        HttpServletRequest request = ServletUtils.getRequest();
        if (ServletUtils.isAjaxRequest(request)) {
            if (CollUtil.isNotEmpty(request.getParameterMap())) {
                model.setRequestParam(CoreCommonUtils.objectToJsonStr(request.getParameterMap()));
            }
        }
        if (result instanceof Exception) {
            model.setExceptionName(result.getClass().getName());
            Exception e = (Exception) result;
            model.setExceptionMessage(e.getMessage());
            model.setStackTrace(e.toString());
        } else {
            model.setRequestResult(Objects.nonNull(result) ? CoreCommonUtils.objectToJsonStr(result) : "");
        }
    }


    /**
     * 获取方法请求参数
     *
     * @param methodSignature ${@link MethodSignature}
     * @param joinPoint       ${@link ProceedingJoinPoint}
     * @return String ${@link String}
     * @author zhouxuanhong
     * @date 2019-06-20 16:33
     */
    private String getMethodParams(MethodSignature methodSignature, ProceedingJoinPoint joinPoint) {
        String params = "";
        JSONObject jsonObject = new JSONObject();
        HttpServletRequest request = ServletUtils.getRequest();
        if (ServletUtils.isAjaxRequest(request)) {
            // 请求参数处理
            String[] parameterNames = methodSignature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            int len = parameterNames.length;
            if (args != null && parameterNames.length == args.length) {
                for (int i = 0; i < len; i++) {
                    Object objectParam = args[i];
                    if (!isFilterObject(objectParam)) {
                        try {
                            jsonObject.put(parameterNames[i], JSONObject.toJSON(objectParam));
                        } catch (Exception e) {
                            log.debug("----------LogAspect---------->getMethodParams:{}", "日志记录获取方法参数转换json异常");
                            if (objectParam != null) {
                                jsonObject.put(parameterNames[i], objectParam.toString());
                            }
                        }
                    }
                }
            }
            params = jsonObject.toJSONString();
            // 去掉参数中有关密码关键字的字符串
            params = params.replaceAll("\"password\":\"[0-9a-zA-Z]+\"", "\"password\":\"******\"");
            params = params.replaceAll("\"newPassword\":\"[0-9a-zA-Z]+\"", "\"newPassword\":\"******\"");
            params = params.replaceAll("\"oldPassword\":\"[0-9a-zA-Z]+\"", "\"oldPassword\":\"******\"");
            params = params.replaceAll("\"Password\":\"[0-9a-zA-Z]+\"", "\"Password\":\"******\"");
            params = params.replaceAll("\"NewPassword\":\"[0-9a-zA-Z]+\"", "\"NewPassword\":\"******\"");
            params = params.replaceAll("\"OldPassword\":\"[0-9a-zA-Z]+\"", "\"OldPassword\":\"******\"");
        }
        return params;
    }


    /**
     * 判断过滤请求方法参数信息
     *
     * @param o 对象信息。
     * @return true-过滤,false-不过来
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
