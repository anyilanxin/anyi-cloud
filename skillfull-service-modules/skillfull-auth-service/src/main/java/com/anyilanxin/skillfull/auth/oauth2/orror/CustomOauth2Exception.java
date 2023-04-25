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
package com.anyilanxin.skillfull.auth.oauth2.orror;


import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * OAuth2Exception异常类
 *
 * @author zhouxuanhong
 * @date 2019-05-27 16:28
 * @since JDK1.8
 */
@JsonSerialize(using = CustomExceptionJacksonSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {
    private final Result<T> error;

    public CustomOauth2Exception(String msg) {
        super(msg);
        this.error = new Result<>(Status.ACCESS_ERROR, msg);
    }

    public CustomOauth2Exception(Result<T> error) {
        super(error.getMessage());
        this.error = error;
    }

    public Result<T> getResult() {
        return this.error;
    }
}
