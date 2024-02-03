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
package com.anyilanxin.anyicloud.coremvc.config;


import cn.zhxu.okhttps.HTTP;
import cn.zhxu.okhttps.MsgConvertor;
import cn.zhxu.okhttps.OkHttps;
import cn.zhxu.okhttps.fastjson2.Fastjson2MsgConvertor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * ok http配置
 *
 * @author xuanhong
 * @date 2023-10-11 11:11
 * @since JDK11
 */

@Configuration
public class OkHttpConfig {

    /**
     * json提交
     */
    @Primary
    @Bean(value = "httpJson")
    public HTTP httpJson() {
        return createBaseBuilder("json").build();
    }


    /**
     * form提交
     */
    @Bean(value = "httpForm")
    public HTTP httpForm() {
        return createBaseBuilder("form")
                .addMsgConvertor(new MsgConvertor.FormConvertor(new Fastjson2MsgConvertor()))
                .build();
    }


    /**
     * 创建基础builder
     *
     * @return {@link HTTP.Builder }
     * @author zxiaozhou
     * @date 2022-12-01 14:39:23
     */
    private HTTP.Builder createBaseBuilder(String type) {
        HTTP.Builder config = HTTP.builder()
                .config((OkHttpClient.Builder builder) -> {
                    // 请求日志
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(logging);
                    // 连接超时时间（默认10秒）
                    builder.connectTimeout(120, TimeUnit.SECONDS);
                    // 写入超时时间（默认10秒）
                    builder.writeTimeout(60, TimeUnit.SECONDS);
                    // 读取超时时间（默认10秒）
                    builder.readTimeout(30, TimeUnit.SECONDS);
                });
        if ("json".equals(type)) {
            return config.bodyType(OkHttps.JSON)
                    .addMsgConvertor(new Fastjson2MsgConvertor());
        } else {
            return config.bodyType(OkHttps.FORM)
                    .addMsgConvertor(new Fastjson2MsgConvertor());
        }
    }
}
