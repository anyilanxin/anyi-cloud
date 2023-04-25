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


package com.anyilanxin.skillfull.corewebflux.base.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corewebflux.utils.ServletUtils;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import reactor.core.publisher.Mono;

/**
 * Controller基类
 *
 * @author zxiaozhou
 * @date 2020-06-22 17:19
 * @since JDK11
 */
@Slf4j
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "403", description = "拒绝访问"),
        @ApiResponse(responseCode = "404", description = "请求路径不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
})
public class BaseController {

    /**
     * 成功响应
     *
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Mono<Result<T>> ok() {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(Status.SUCCESS));
    }

    /**
     * 成功响应
     *
     * @return Result<String> ${@link Result<String>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static Mono<Result<String>> ok(String message) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(Status.SUCCESS, message));
    }

    /**
     * 成功响应
     *
     * @param data ${@link Object} 成功响应数据
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Mono<Result<T>> ok(T data) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(Status.SUCCESS, data));
    }

    /**
     * 成功响应
     *
     * @param data ${@link Object} 成功响应数据
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Result<T> getResult(T data) {
        ServletUtils.removeServerHttpRequest();
        return new Result<>(Status.SUCCESS, data);
    }

    /**
     * 成功响应
     *
     * @param status ${@link Object} 成功响应数据
     * @return Result<T> ${@link Result< T >} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static Mono<Result<Object>> ok(Status status) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(status));
    }

    /**
     * 成功响应
     *
     * @param data    ${@link Object} 成功响应数据
     * @param message ${@link String} 成功响应消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Mono<Result<T>> ok(T data, String message) {
        ServletUtils.removeServerHttpRequest();
        Result<T> result = new Result<>(Status.SUCCESS, data);
        result.setMessage(message);
        return Mono.just(result);
    }

    /**
     * 响应失败
     *
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<Result<T>> fail() {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(Status.ERROR));
    }

    /**
     * 响应失败
     *
     * @param status ${@link Status} 失败状态
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<Result<T>> fail(Status status) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(status));
    }

    /**
     * 响应失败
     *
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<Result<T>> fail(String message) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(Status.ERROR, message));
    }

    /**
     * 响应失败
     *
     * @param status  ${@link Status} 失败状态
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<Result<T>> fail(Status status, String message) {
        ServletUtils.removeServerHttpRequest();
        Result<T> result = new Result<>(status);
        result.setMessage(message);
        return Mono.just(result);
    }

    /**
     * 响应失败
     *
     * @param code    ${@link Integer} 失败状态码
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<Result<T>> fail(Integer code, String message) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new Result<>(code, message));
    }
}
