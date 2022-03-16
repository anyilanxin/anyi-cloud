// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coremvc.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.coremvc.aspect.model.OperateModel;
import indi.zxiaozhou.skillfull.coremvc.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * 系统日志，切面处理类
 *
 * @author zxiaozhou
 * @date 2020-08-27 16:59
 * @since JDK11
 */
//@Aspect
//@Component
@Slf4j
//@RequiredArgsConstructor
public class LogAspect {
    private static final String REQUEST_JSON_TYPE = "application";


    @Pointcut("@annotation(indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog)")
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
            if (CollectionUtil.isNotEmpty(request.getParameterMap())) {
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
