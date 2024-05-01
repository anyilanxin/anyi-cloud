/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.system.core.handler;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.utils.AnYiCoreCommonUtils;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.cloud.oauth2common.enums.DataFormatType;
import com.anyilanxin.cloud.oauth2common.model.DataTypeModel;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

/**
 * 异常处理器
 *
 * @author zxh
 * @date 2020-06-22 17:34
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends AnYiBaseController {
    /**
     * 处理所有不可知的异常
     *
     * @param e ${@link Exception}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:21
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------处理所有不可知的异常------>handleException--->异常消息:\n{}", e.getMessage());
        Throwable cause = e.getCause();
        if (Objects.nonNull(cause)) {
            if (cause instanceof AnYiResponseException) {
                AnYiResponseException exception = (AnYiResponseException) cause;
                AnYiResult<Object> result = exception.getResult();
                return fail(result.getCode(), result.getMessage());
            } else if (Objects.nonNull(cause.getCause()) && cause.getCause() instanceof AnYiResponseException) {
                AnYiResponseException exception = (AnYiResponseException) cause.getCause();
                AnYiResult<Object> result = exception.getResult();
                return fail(result.getCode(), result.getMessage());
            }
            String str = AnYiCoreCommonUtils.getStackTrace(e);
            if (StringUtils.isNotBlank(str)) {
                return fail(str);
            }
        }
        response.setStatus(HttpStatus.OK.value());
        if (cause instanceof AccessDeniedException accessDeniedException) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            DataTypeModel dataTypeModel = new DataTypeModel();
            Object dataType = request.getAttribute(AnYiSecurityConstants.DATA_FORMAT);
            if (Objects.nonNull(dataType) && dataType instanceof DataTypeModel typeModel) {
                dataTypeModel = typeModel;
            }
            if (dataTypeModel.getType() == DataFormatType.NORMAL) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("error", accessDeniedException.getMessage());
                return parameters;
            }
        }
        return fail("服务器出问题了:" + e.getMessage());
    }


    /**
     * 处理自定义异常
     *
     * @param e ${@link AnYiResponseException} 处理异常
     * @return AnYiResult ${@link AnYiResult} 响应前端
     * @author zxh
     * @date 2020-08-27 15:17
     */
    @ExceptionHandler(AnYiResponseException.class)
    @ResponseStatus(HttpStatus.OK)
    public AnYiResult<String> handlerResponseException(AnYiResponseException e) {
        e.printStackTrace();
        AnYiResult<Object> result = e.getResult();
        log.error("------------GlobalExceptionHandler------处理自定义异常------>handlerResponseException:\n{}", e.getMessage());
        return fail(result.getCode(), result.getMessage());
    }


    /**
     * 处理请求参数校验(普通传参)异常
     *
     * @param e ${@link BindException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2019-06-18 09:35
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    AnYiResult<String> handleBindException(BindException e) {
        StringBuilder sb = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : e.getAllErrors()) {
            ConstraintViolationImpl unwrap = error.unwrap(ConstraintViolationImpl.class);
            String defaultMessage = error.getDefaultMessage();
            Map messageParameters = unwrap.getMessageParameters();
            if (CollUtil.isNotEmpty(messageParameters)) {
                Object dynamicMessage = messageParameters.get(CommonCoreConstant.DYNAMIC_VALIDATE_MESSAGE_KEY);
                if (Objects.nonNull(dynamicMessage)) {
                    defaultMessage = dynamicMessage.toString();
                }
            }
            sb.append(",").append(defaultMessage);
        }
        String errMeg = sb.toString().replaceFirst(",", "");
        log.error("------------GlobalExceptionHandler------处理请求参数校验(普通传参)异常------>handleBindException:\n{}", errMeg);
        return fail(AnYiResultStatus.VERIFICATION_FAILED, errMeg);
    }


    /**
     * 处理请求参数校验(普通传参)异常
     *
     * @param e ${@link ConstraintViolationException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2019-06-18 09:35
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    AnYiResult<String> handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String defaultMessage = violation.getMessage();
            if (violation instanceof ConstraintViolationImpl constraintViolation) {
                Map messageParameters = constraintViolation.getMessageParameters();
                if (CollUtil.isNotEmpty(messageParameters)) {
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
        return fail(AnYiResultStatus.VERIFICATION_FAILED, errMeg);
    }


    /**
     * 处理请求参数校验(实体对象传参)异常
     *
     * @param e ${@link MethodArgumentNotValidException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:27
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public AnYiResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            ConstraintViolationImpl unwrap = error.unwrap(ConstraintViolationImpl.class);
            String defaultMessage = error.getDefaultMessage();
            Map messageParameters = unwrap.getMessageParameters();
            if (CollUtil.isNotEmpty(messageParameters)) {
                Object dynamicMessage = messageParameters.get(CommonCoreConstant.DYNAMIC_VALIDATE_MESSAGE_KEY);
                if (Objects.nonNull(dynamicMessage)) {
                    defaultMessage = dynamicMessage.toString();
                }
            }
            sb.append(",").append(defaultMessage);
        }
        String errMeg = sb.toString().replaceFirst(",", "");
        log.error("------------GlobalExceptionHandler------处理请求参数校验(实体对象传参)异常------>handleMethodArgumentNotValidException:\n{}", errMeg);
        return fail(AnYiResultStatus.VERIFICATION_FAILED, errMeg);
    }


    /**
     * 处理数据库数据重复异常
     *
     * @param e ${@link DuplicateKeyException}
     * @return AnYiResult${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:27
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    public AnYiResult<String> handleDuplicateKeyException(DuplicateKeyException e) {
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
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public AnYiResult<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler-----处理不支持请求方式------->handleHttpRequestMethodNotSupportedException:\n{}", e.getMessage());
        return fail("请求方式不支持:" + e.getMessage());
    }


    /**
     * 处理sql语法错误
     *
     * @param e ${@link BadSqlGrammarException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.OK)
    public AnYiResult<?> handleBadSqlGrammarException(BadSqlGrammarException e) {
        log.error("------------GlobalExceptionHandler------处理sql语法错误------>handleBadSqlGrammarException:\n{}", e.getMessage());
        e.printStackTrace();
        return fail("数据库sql语法错误:" + e.getMessage());
    }


    /**
     * post请求缺少body参数
     *
     * @param e ${@link HttpMessageNotReadableException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public AnYiResult<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------post请求缺少body参数------>handleHttpMessageNotReadableException:\n{}", e.getMessage());
        return fail("post请求缺少body参数:" + e.getMessage());
    }


    /**
     * 请求地址不存在
     *
     * @param e ${@link NoHandlerFoundException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AnYiResult<String> handleNoSuchElementException(NoHandlerFoundException e, ServletServerHttpRequest request) {
        e.printStackTrace();
        log.info("------------GlobalExceptionHandler------请求地址不存在------>handleNoSuchElementException:\n{}", request.getURI().getPath());
        return fail("请求地址不存在:" + e.getMessage());
    }


    /**
     * 处理数据库唯一性校验失败异常
     *
     * @param e ${@link SQLIntegrityConstraintViolationException}
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AnYiResult<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
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
     * @return AnYiResult ${@link AnYiResult}
     * @author zxh
     * @date 2020-08-27 15:28
     */
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.OK)
    public AnYiResult<String> handleFeignException(FeignException e) {
        e.printStackTrace();
        log.error("------------GlobalExceptionHandler------feign请求异常------>handleFeignException--->异常消息:\n{}", e.getLocalizedMessage());
        String resultStringMsg = e.contentUTF8();
        if (StringUtils.isNotBlank(resultStringMsg)) {
            return JSONObject.parseObject(resultStringMsg, new TypeReference<AnYiResult<String>>() {
            });
        } else {
            return fail(e.getLocalizedMessage());
        }
    }

}
