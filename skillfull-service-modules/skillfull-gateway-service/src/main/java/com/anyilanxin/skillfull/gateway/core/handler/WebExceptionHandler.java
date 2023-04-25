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


package com.anyilanxin.skillfull.gateway.core.handler;

import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.gateway.utils.CorsWebUtils;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * @author zxiaozhou
 * @date 2020-09-11 18:10
 * @since JDK11
 */
@Slf4j
public class WebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static final String SERVICE_NOT_FOUND =
            "503 SERVICE_UNAVAILABLE \"Unable to find instance for ";

    public WebExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties webProperties,
            ErrorProperties errorProperties,
            ApplicationContext applicationContext) {
        super(errorAttributes, webProperties.getResources(), errorProperties, applicationContext);
    }

    /**
     * 构建响应消息
     *
     * @param request ${@link ServerRequest}
     * @param options ${@link ErrorAttributeOptions}
     * @return Map<String, Object> ${@link Map<String, Object>}
     * @author zxiaozhou
     * @date 2020-09-11 19:26
     */
    @Override
    protected Map<String, Object> getErrorAttributes(
            ServerRequest request, ErrorAttributeOptions options) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        int code = 0;
        Throwable error = super.getError(request);
        if (error instanceof ResponseStatusException) {
            status = HttpStatus.NOT_FOUND.value();
        } else if (error instanceof ResponseException) {
            ResponseException responseException = (ResponseException) error;
            code = responseException.getResult().getCode();
        } else if (error instanceof InvalidTokenException) {
            InvalidTokenException invalidTokenException = (InvalidTokenException) error;
            code = Status.ACCESS_ERROR.getCode();
            status = HttpStatus.UNAUTHORIZED.value();
        } else if (error instanceof UnauthorizedClientException) {
            UnauthorizedClientException unauthorizedClientException = (UnauthorizedClientException) error;
            code = Status.ACCESS_ERROR.getCode();
            status = HttpStatus.UNAUTHORIZED.value();
        } else if (error instanceof UnauthorizedUserException) {
            UnauthorizedUserException unauthorizedUserException = (UnauthorizedUserException) error;
            code = Status.ACCESS_ERROR.getCode();
            status = HttpStatus.UNAUTHORIZED.value();
        }
        return response(status, code, this.buildMessage(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes ${@link ErrorAttributes}
     * @return RouterFunction<ServerResponse> ${@link RouterFunction<ServerResponse>}
     * @author zxiaozhou
     * @date 2020-09-11 19:25
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 最终响应数据渲染,去掉不想显示的部分
     *
     * @param request ${@link ServerRequest}
     * @return ServerResponse> ${@link ServerResponse>}
     * @author zxiaozhou
     * @date 2020-09-11 19:25
     */
    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> error =
                getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        int httpStatus = getHttpStatus(error);
        error.remove("status");
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(v -> CorsWebUtils.addCorsHeaders(request.exchange()))
                .body(BodyInserters.fromValue(error));
    }

    /**
     * 构建异常信息
     *
     * @param request
     * @param ex
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        StringBuilder message = new StringBuilder("请求失败：");
        message.append(request.methodName());
        message.append(", ");
        message.append(request.requestPath());
        message.append(", ");
        if (ex != null) {
            String exMessage = ex.getMessage();
            if (ex instanceof ResponseException) {
                ResponseException responseException = (ResponseException) ex;
                exMessage = responseException.getResult().getMessage();
            } else if (ex instanceof WebExchangeBindException) {
                WebExchangeBindException webExchangeBindException = (WebExchangeBindException) ex;
                List<ObjectError> allErrors = webExchangeBindException.getBindingResult().getAllErrors();
                StringBuilder sb = new StringBuilder();
                Set<ObjectError> violations = new HashSet<>(allErrors);
                for (ObjectError violation : violations) {
                    sb.append(",").append(violation.getDefaultMessage());
                }
                exMessage = sb.toString().replaceFirst(",", "");
            } else if (ex instanceof NotFoundException) {
                NotFoundException notFoundException = (NotFoundException) ex;
                String messageInfo = notFoundException.getMessage();
                if (StringUtils.isNotBlank(messageInfo)) {
                    messageInfo = messageInfo.replace(SERVICE_NOT_FOUND, "").replace("\"", "");
                }
                exMessage = messageInfo + "服务未发现";
            }
            message.append(exMessage);
        }
        return message.toString();
    }

    /**
     * 构建异常响应消息
     *
     * @param status       ${@link Integer} Http自身状态码
     * @param code         ${@link Integer} 自定义状态码
     * @param errorMessage ${@link String} 异常消息
     * @return Map<String, Object> ${@link Map<String, Object>}
     * @author zxiaozhou
     * @date 2020-09-11 19:23
     */
    public static Map<String, Object> response(int status, int code, String errorMessage) {
        Map<String, Object> result = new HashMap<>(4);
        result.put("code", code);
        result.put("success", status == HttpStatus.OK.value() && code == 0);
        result.put("status", status);
        result.put("message", errorMessage);
        return result;
    }
}
