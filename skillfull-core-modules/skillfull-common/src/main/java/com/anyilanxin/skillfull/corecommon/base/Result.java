/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.corecommon.base;

import com.anyilanxin.skillfull.corecommon.constant.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
* 响应结果封装
*
* @author zxiaozhou
* @date 2020-06-22 16:29
* @since JDK11
*/
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Schema
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 2824340746431686918L;

    @Schema(title = "响应状态码")
    private int code;

    @Schema(title = "成功标识")
    private boolean success;

    @Schema(title = "响应消息")
    private String message;

    @Schema(title = "响应数据")
    private T data;

    @Schema(title = "响应时间")
    private long timestamp;

    public Result() {}

    public Result(Status status) {
        this.setSuccess(status.getCode() == 0);
        this.setCode(status.getCode());
        this.setMessage(status.getMessage());
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Status status, T data) {
        this.setSuccess(status.getCode() == 0);
        this.setCode(status.getCode());
        this.setMessage(status.getMessage());
        this.setData(data);
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Status status, String message) {
        this.setSuccess(status.getCode() == 0);
        this.setCode(status.getCode());
        this.setMessage(message);
        this.setData(data);
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Status status, String message, T data) {
        this.setSuccess(status.getCode() == 0);
        this.setCode(status.getCode());
        this.setMessage(StringUtils.isNotBlank(message) ? message : status.getMessage());
        this.setData(data);
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, T data) {
        this.setSuccess(code == 0);
        this.setCode(code);
        this.setData(data);
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, String message) {
        this.setSuccess(code == 0);
        this.setCode(code);
        this.setMessage(message);
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, String message, T data) {
        this.setSuccess(code == 0);
        this.setCode(code);
        this.setData(data);
        this.setMessage(message);
        this.timestamp = System.currentTimeMillis();
    }
}
