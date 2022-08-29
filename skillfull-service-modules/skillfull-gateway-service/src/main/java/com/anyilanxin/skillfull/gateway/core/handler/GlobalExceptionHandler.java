// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.core.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.corewebflux.base.controller.BaseController;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Objects;
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
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    public Mono<Result<String>> handlerResponseException(ResponseException e) {
        Result<Object> result = e.getResult();
        log.error("------------GlobalExceptionHandler------处理自定义异常------>handlerResponseException:{}", e.getMessage());
        return fail(result.getCode(), result.getMessage());
    }


    /**
     * 处理鉴权异常
     *
     * @param e ${@link UnauthorizedUserException} 处理异常
     * @return Result ${@link Result} 响应前端
     * @author zxiaozhou
     * @date 2020-08-27 15:17
     */
    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<Result<String>> handlerUnauthorizedUserException(UnauthorizedUserException e) {
        e.printStackTrace();
        log.error("-----------------处理自定义异常------>handlerUnauthorizedUserException:\n{}", e.getMessage());
        return fail(Status.TOKEN_EXPIRED, e.getMessage());
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
    @ResponseStatus(HttpStatus.OK)
    public Mono<Result<String>> handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String defaultMessage = violation.getMessage();
            if (violation instanceof ConstraintViolationImpl) {
                ConstraintViolationImpl constraintViolation = (ConstraintViolationImpl) violation;
                Map messageParameters = constraintViolation.getMessageParameters();
                if (CollectionUtil.isNotEmpty(messageParameters)) {
                    Object dynamicMessage = messageParameters.get(CommonCoreConstant.DYNAMIC_VALIDATE_MESSAGE_KEY);
                    if (Objects.nonNull(dynamicMessage)) {
                        defaultMessage = dynamicMessage.toString();
                    }
                }
            }
            sb.append(",").append(defaultMessage);
        }
        String errMeg = sb.toString().replaceFirst(",", "");
        log.error("------------GlobalExceptionHandler------处理请求参数校验(普通传参)异常------>handleConstraintViolationException:\n{}", errMeg);
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
    @ResponseStatus(HttpStatus.OK)
    public Mono<Result<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            ConstraintViolationImpl unwrap = error.unwrap(ConstraintViolationImpl.class);
            String defaultMessage = error.getDefaultMessage();
            Map messageParameters = unwrap.getMessageParameters();
            if (CollectionUtil.isNotEmpty(messageParameters)) {
                Object dynamicMessage = messageParameters.get(CommonCoreConstant.DYNAMIC_VALIDATE_MESSAGE_KEY);
                if (Objects.nonNull(dynamicMessage)) {
                    defaultMessage = dynamicMessage.toString();
                }
            }
            sb.append(",").append(defaultMessage);
        }
        String errMeg = sb.toString().replaceFirst(",", "");
        log.error("------------GlobalExceptionHandler------处理请求参数校验(实体对象传参)异常------>handleMethodArgumentNotValidException:\n{}", errMeg);
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
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
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
