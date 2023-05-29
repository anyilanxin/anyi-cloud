/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.gateway.core.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.anyicloud.corewebflux.base.controller.BaseController;
import feign.FeignException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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

/**
 * 异常处理器
 *
 * @author zxh
 * @date 2020-06-22 17:34
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    /**
     * 处理所有不可知的异常
     *
     * @param e ${@link Exception}
     * @return Mono<Result < String>> ${@link Mono<Result<String>>}
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
