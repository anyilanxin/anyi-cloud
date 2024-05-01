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

package com.anyilanxin.cloud.loggingmvc.feign;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

import static feign.Util.decodeOrDefault;
import static feign.form.util.CharsetUtil.UTF_8;

/**
 * feign日志拦截
 *
 * @author zxh
 * @date 2022-07-05 16:10
 * @since 1.0.0
 */
@Slf4j
public class AnYiFeignLogger extends Logger {

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) {
        Request request = response.request();
        byte[] requestBodyParamsByte = request.body();
        String bodyParams = Objects.isNull(requestBodyParamsByte) ? "" : new String(requestBodyParamsByte);
        log.info("------11------------请求方法------>\n{}", response.request().httpMethod());
        log.info("------22------------请求地址------>\n{}", response.request().url());
        log.info("------33------------请求body参数------>\n{}", bodyParams);
        log.info("------44------------请求query参数------>\n{}", request.requestTemplate().queries());
        log.info("------55------------请求响应状态------>\n{}", response.status());
        int status = response.status();
        String content = "";
        if (Objects.nonNull(response.body()) && status == 200) {
            byte[] bodyData;
            try {
                bodyData = Util.toByteArray(response.body().asInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (bodyData.length > 0) {
                content = decodeOrDefault(bodyData, UTF_8, "Binary data");
            }
            response = response.toBuilder().body(bodyData).build();
        }
        log.info("--------66----------响应参数------>\n{}", content);
        return response;
    }


    @Override
    protected void log(String configKey, String format, Object... args) {
    }

}
