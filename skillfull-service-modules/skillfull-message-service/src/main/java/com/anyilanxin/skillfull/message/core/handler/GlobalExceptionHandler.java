// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.core.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
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
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:21
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------处理所有不可知的异常------>handleException--->异常消息:\n{}", e.getMessage());
        Throwable cause = e.getCause();
        if (Objects.nonNull(cause)) {
            if (cause instanceof ResponseException) {
                ResponseException exception = (ResponseException) cause;
                Result<Object> result = exception.getResult();
                return fail(result.getCode(), result.getMessage());
            } else if (Objects.nonNull(cause.getCause()) && cause.getCause() instanceof ResponseException) {
                ResponseException exception = (ResponseException) cause.getCause();
                Result<Object> result = exception.getResult();
                return fail(result.getCode(), result.getMessage());
            }
            String str = CoreCommonUtils.getStackTrace(e);
            if (StringUtils.isNotBlank(str)) {
                return fail(str);
            }
        }
        return fail("服务器出问题了:" + e.getMessage());
    }


    /**
     * 处理自定义异常
     *
     * @param e ${@link ResponseException} 处理异常
     * @return Result ${@link Result} 响应前端
     * @author zxiaozhou
     * @date 2020-08-27 15:17
     */
    @ExceptionHandler(ResponseException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handlerResponseException(ResponseException e) {
        e.printStackTrace();
        Result<Object> result = e.getResult();
        log.error("------------GlobalExceptionHandler------处理自定义异常------>handlerResponseException:\n{}", e.getMessage());
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
    public Result<String> handlerUnauthorizedUserException(UnauthorizedUserException e) {
        e.printStackTrace();
        log.error("-----------------处理自定义异常------>handlerUnauthorizedUserException:\n{}", e.getMessage());
        return fail(Status.TOKEN_EXPIRED, e.getMessage());
    }


    /**
     * 处理请求参数校验(普通传参)异常
     *
     * @param e ${@link BindException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2019-06-18 09:35
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    Result<String> handleBindException(BindException e) {
        StringBuilder sb = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
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
        log.error("------------GlobalExceptionHandler------处理请求参数校验(普通传参)异常------>handleBindException:\n{}", errMeg);
        return fail(Status.VERIFICATION_FAILED, errMeg);
    }


    /**
     * 处理请求参数校验(普通传参)异常
     *
     * @param e ${@link ConstraintViolationException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2019-06-18 09:35
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    Result<String> handleConstraintViolationException(ConstraintViolationException e) {
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
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:27
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
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
     * @return Result${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:27
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        e.printStackTrace();
        String errMsg = e.getLocalizedMessage();
        log.error("------------GlobalExceptionHandler------处理数据库数据重复异常------>handleDuplicateKeyException:\n{}", errMsg);
        if (StringUtils.isNotBlank(errMsg)) {
            String[] errMsgs = errMsg.split("###");
            if (errMsgs.length >= 2) {
                errMsg = errMsgs[1];
                errMsgs = errMsg.split(":");
                errMsg = errMsgs[errMsgs.length - 1].replaceAll(" Duplicate entry ", "").replaceAll("\n", "");
            }
        }
        return fail("数据库关键信息重复:" + errMsg);
    }


    /**
     * 处理不支持请求方式
     *
     * @param e ${@link HttpRequestMethodNotSupportedException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler-----处理不支持请求方式------->handleHttpRequestMethodNotSupportedException:\n{}", e.getMessage());
        return fail("请求方式不支持:" + e.getMessage());
    }


    /**
     * 处理sql语法错误
     *
     * @param e ${@link BadSqlGrammarException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBadSqlGrammarException(BadSqlGrammarException e) {
        log.error("------------GlobalExceptionHandler------处理sql语法错误------>handleBadSqlGrammarException:\n{}", e.getMessage());
        e.printStackTrace();
        return fail("数据库sql语法错误:" + e.getMessage());
    }


    /**
     * post请求缺少body参数
     *
     * @param e ${@link HttpMessageNotReadableException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------post请求缺少body参数------>handleHttpMessageNotReadableException:\n{}", e.getMessage());
        return fail("post请求缺少body参数:" + e.getMessage());
    }


    /**
     * 请求地址不存在
     *
     * @param e ${@link NoHandlerFoundException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<String> handleNoSuchElementException(NoHandlerFoundException e, ServletServerHttpRequest request) {
        e.printStackTrace();
        log.info("------------GlobalExceptionHandler------请求地址不存在------>handleNoSuchElementException:\n{}", request.getURI().getPath());
        return fail("请求地址不存在:" + e.getMessage());
    }


    /**
     * 处理数据库唯一性校验失败异常
     *
     * @param e ${@link SQLIntegrityConstraintViolationException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------处理数据库唯一性校验失败异常------>handleSQLIntegrityConstraintViolationException:\n{}", e.getMessage());
        String errMsg = e.getLocalizedMessage();
        if (StringUtils.isNotBlank(errMsg)) {
            String[] errMsgs = errMsg.split("###");
            if (errMsgs.length >= 2) {
                errMsgs = errMsgs[1].split(":");
                errMsg = errMsgs[errMsgs.length - 1];
                errMsg = errMsg.replaceAll(" Field ", "").replaceAll("\n", "");
            }
        }
        return fail("数据库必填字段没有值:" + errMsg);
    }


    /**
     * feign请求异常
     *
     * @param e ${@link FeignException}
     * @return Result ${@link Result}
     * @author zxiaozhou
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleFeignException(FeignException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------feign请求异常------>handleFeignException--->异常消息:\n{}", e.getLocalizedMessage());
        String resultStringMsg = e.contentUTF8();
        if (StringUtils.isNotBlank(resultStringMsg)) {
            return JSONObject.parseObject(resultStringMsg, new TypeReference<Result<String>>() {
            });
        } else {
            return fail(e.getLocalizedMessage());
        }
    }

}
