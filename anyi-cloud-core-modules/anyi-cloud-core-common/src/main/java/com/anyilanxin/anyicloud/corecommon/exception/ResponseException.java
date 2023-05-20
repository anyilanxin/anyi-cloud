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

package com.anyilanxin.anyicloud.corecommon.exception;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 响应异常
 *
 * @author zxh
 * @date 2020-06-22 16:24
 * @since 1.0.0
 */
public class ResponseException extends RuntimeException {
    private static final long serialVersionUID = 7207809155561786625L;

    /**
     * 错误异常结果
     */
    private final Result<Object> result;

    public ResponseException() {
        super(Status.ERROR.getMessage());
        this.result = new Result<>(Status.ERROR);
    }


    /**
     * 构造函数
     *
     * @param status 响应代码
     */
    public ResponseException(Status status) {
        super(status.getMessage());
        this.result = new Result<>(status);
    }


    /**
     * 构造函数
     *
     * @param status 响应代码
     * @param data   业务数据
     */
    public ResponseException(Status status, Object data) {
        super(status.getMessage());
        this.result = new Result<>(status, data);
    }


    /**
     * 构造函数
     *
     * @param status  响应代码
     * @param message 异常消息
     */
    public ResponseException(Status status, String message) {
        super(message);
        this.result = new Result<>(status, message);
    }


    /**
     * 构造函数
     *
     * @param message 消息提示
     */
    public ResponseException(String message) {
        super(message);
        this.result = new Result<>(Status.ERROR, message);
    }


    /**
     * 构造函数
     *
     * @param code    响应代码
     * @param message 消息提示
     */
    public ResponseException(int code, String message) {
        super(message);
        this.result = new Result<>(code, message);
    }


    /**
     * 构造函数
     *
     * @param result HTTP响应接口输出结果实体
     */
    public ResponseException(Result<Object> result) {
        super(result.getMessage());
        this.result = result;
    }


    /**
     * 获取错误堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }


    public Result<Object> getResult() {
        return result;
    }


    /**
     * 获取错误堆栈信息
     *
     * @return
     */
    public String getStackTraceString() {
        return getStackTrace(this);
    }
}