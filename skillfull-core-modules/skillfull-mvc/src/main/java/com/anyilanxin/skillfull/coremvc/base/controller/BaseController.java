/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.coremvc.base.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

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
    public static <T> Result<T> ok() {
        return new Result<>(Status.SUCCESS);
    }

    /**
     * 成功响应
     *
     * @return Result<String> ${@link Result<String>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static Result<String> ok(String message) {
        return new Result<>(Status.SUCCESS, message);
    }


    /**
     * 成功响应
     *
     * @param data ${@link Object} 成功响应数据
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Result<T> ok(T data) {
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
    public static Result<Object> ok(Status status) {
        return new Result<>(status);
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
    public static <T> Result<T> ok(T data, String message) {
        Result<T> result = new Result<>(Status.SUCCESS, data);
        result.setMessage(message);
        return result;
    }


    /**
     * 响应失败
     *
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail() {
        return new Result<>(Status.ERROR);
    }


    /**
     * 响应失败
     *
     * @param status ${@link Status} 失败状态
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail(Status status) {
        return new Result<>(status);
    }

    /**
     * 响应失败
     *
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(Status.ERROR, message);
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
    public static <T> Result<T> fail(Status status, String message) {
        Result<T> result = new Result<>(status);
        result.setMessage(message);
        return result;
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
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }
}
