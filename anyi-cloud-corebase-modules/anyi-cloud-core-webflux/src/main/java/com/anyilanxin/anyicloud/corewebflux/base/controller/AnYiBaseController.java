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

package com.anyilanxin.anyicloud.corewebflux.base.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corewebflux.utils.ServletUtils;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Controller基类
 *
 * @author zxh
 * @date 2020-06-22 17:19
 * @since 1.0.0
 */
@Slf4j
@ApiResponses({@ApiResponse(responseCode = "200", description = "成功")})
public class AnYiBaseController {

    /**
     * 成功响应
     *
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:16
     */
    public static <T> Mono<AnYiResult<T>> ok() {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(AnYiResultStatus.SUCCESS));
    }


    /**
     * 成功响应
     *
     * @return AnYiResult<String> ${@link AnYiResult <String>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:16
     */
    public static Mono<AnYiResult<String>> ok(String message) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(AnYiResultStatus.SUCCESS, message));
    }


    /**
     * 成功响应
     *
     * @param data ${@link Object} 成功响应数据
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:16
     */
    public static <T> Mono<AnYiResult<T>> ok(T data) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(AnYiResultStatus.SUCCESS, data));
    }


    /**
     * 成功响应
     *
     * @param data ${@link Object} 成功响应数据
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:16
     */
    public static <T> AnYiResult<T> getResult(T data) {
        ServletUtils.removeServerHttpRequest();
        return new AnYiResult<>(AnYiResultStatus.SUCCESS, data);
    }


    /**
     * 成功响应
     *
     * @param status ${@link Object} 成功响应数据
     * @return AnYiResult<T> ${@link AnYiResult < T >} 响应信息
     * @author zxh
     * @date 2020-06-22 17:16
     */
    public static Mono<AnYiResult<Object>> ok(AnYiResultStatus status) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(status));
    }


    /**
     * 成功响应
     *
     * @param data    ${@link Object} 成功响应数据
     * @param message ${@link String} 成功响应消息
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:16
     */
    public static <T> Mono<AnYiResult<T>> ok(T data, String message) {
        ServletUtils.removeServerHttpRequest();
        AnYiResult<T> result = new AnYiResult<>(AnYiResultStatus.SUCCESS, data);
        result.setMessage(message);
        return Mono.just(result);
    }


    /**
     * 响应失败
     *
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<AnYiResult<T>> fail() {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(AnYiResultStatus.ERROR));
    }


    /**
     * 响应失败
     *
     * @param status ${@link AnYiResultStatus} 失败状态
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<AnYiResult<T>> fail(AnYiResultStatus status) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(status));
    }


    /**
     * 响应失败
     *
     * @param message ${@link String} 失败消息
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<AnYiResult<T>> fail(String message) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(AnYiResultStatus.ERROR, message));
    }


    /**
     * 响应失败
     *
     * @param status  ${@link AnYiResultStatus} 失败状态
     * @param message ${@link String} 失败消息
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<AnYiResult<T>> fail(AnYiResultStatus status, String message) {
        ServletUtils.removeServerHttpRequest();
        AnYiResult<T> result = new AnYiResult<>(status);
        result.setMessage(message);
        return Mono.just(result);
    }


    /**
     * 响应失败
     *
     * @param code    ${@link Integer} 失败状态码
     * @param message ${@link String} 失败消息
     * @return AnYiResult<T> ${@link AnYiResult <T>} 响应信息
     * @author zxh
     * @date 2020-06-22 17:17
     */
    public static <T> Mono<AnYiResult<T>> fail(Integer code, String message) {
        ServletUtils.removeServerHttpRequest();
        return Mono.just(new AnYiResult<>(code, message));
    }
}
