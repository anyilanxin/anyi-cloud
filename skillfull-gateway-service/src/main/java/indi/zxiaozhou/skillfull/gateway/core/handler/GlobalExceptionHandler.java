// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.core.handler;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import feign.FeignException;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.corewebflux.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;


/**
 * 异常处理器
 *
 * @author zxiaozhou
 * @date 2020-06-22 17:34
 * @since JDK11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    /**
     * 处理所有不可知的异常
     *
     * @param e ${@link Exception}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxiaozhou
     * @date 2020-08-27 15:21
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<String>> handleException(Exception e) {
        String str = CoreCommonUtils.getStackTrace(e);
        log.error("------------GlobalExceptionHandler------处理所有不可知的异常------>handleException:{}", str);
        if (StringUtils.isNotBlank(str)) {
            return fail(str);
        }
        return fail("服务器出问题了:" + e.getMessage());
    }


    /**
     * 处理自定义异常
     *
     * @param e ${@link ResponseException} 处理异常
     * @return Mono<Result < String>> ${@link Mono<Result<String>>} 响应前端
     * @author zxiaozhou
     * @date 2020-08-27 15:17
     */
    @ExceptionHandler(ResponseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<String>> handlerResponseException(ResponseException e) {
        Result<Object> result = e.getResult();
        log.error("------------GlobalExceptionHandler------处理自定义异常------>handlerResponseException:{}", e.getMessage());
        return fail(result.getCode(), result.getMessage());
    }


    /**
     * 处理请求参数校验(普通传参)异常
     *
     * @param e ${@link ConstraintViolationException}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxiaozhou
     * @date 2019-06-18 09:35
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<String>> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            sb.append(",").append(violation.getMessage());
        }
        String errMeg = sb.toString().replaceFirst(",", "");
        log.error("------------GlobalExceptionHandler------处理请求参数校验(普通传参)异常------>handleConstraintViolationException:{}", errMeg);
        return fail(Status.VERIFICATION_FAILED, errMeg);
    }


    /**
     * 处理请求参数校验(实体对象传参)异常
     *
     * @param e ${@link MethodArgumentNotValidException}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxiaozhou
     * @date 2020-08-27 15:27
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        for (ObjectError error : errorList) {
            sb.append(",").append(error.getDefaultMessage());
        }
        String errMeg = sb.toString().replaceFirst(",", "");
        log.error("------------GlobalExceptionHandler------处理请求参数校验(实体对象传参)异常------>handleMethodArgumentNotValidException:{}", errMeg);
        return fail(Status.VERIFICATION_FAILED, errMeg);
    }


    /**
     * 处理数据库数据重复异常
     *
     * @param e ${@link DuplicateKeyException}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxiaozhou
     * @date 2020-08-27 15:27
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<String>> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("------------GlobalExceptionHandler------处理数据库数据重复异常------>handleDuplicateKeyException:{}", e.getMessage());
        return fail("数据库中已存在该记录:" + e.getMessage());
    }


    /**
     * post请求缺少body参数
     *
     * @param e ${@link HttpMessageNotReadableException}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<String>> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("------------GlobalExceptionHandler------post请求缺少body参数------>httpMessageNotReadableException:{}", e.getMessage());
        return fail("post请求缺少body参数:" + e.getMessage());
    }


    /**
     * feign请求异常
     *
     * @param e ${@link FeignException}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Result<Object>> feignException(FeignException e) {
        log.error("------------GlobalExceptionHandler------feign异常------>feignException--->\n异常消息:{}", e.getLocalizedMessage());
        String resultStringMsg = e.contentUTF8();
        if (StringUtils.isNotBlank(resultStringMsg)) {
            return Mono.just(JSONObject.parseObject(resultStringMsg, new TypeReference<Result<Object>>() {
            }));
        } else {
            return fail(e.getLocalizedMessage());
        }
    }
}
