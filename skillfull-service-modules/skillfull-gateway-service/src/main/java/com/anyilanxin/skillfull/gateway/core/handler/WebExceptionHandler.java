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

import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.gateway.utils.CorsWebUtils;
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

import java.util.*;

/**
 * @author zxiaozhou
 * @date 2020-09-11 18:10
 * @since JDK11
 */
@Slf4j
public class WebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static final String SERVICE_NOT_FOUND = "503 SERVICE_UNAVAILABLE \"Unable to find instance for ";

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
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
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
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
        Map<String, Object> error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        int httpStatus = getHttpStatus(error);
        error.remove("status");
        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
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
