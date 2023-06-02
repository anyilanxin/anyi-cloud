/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.stream.component;

import com.anyilanxin.anyicloud.stream.constant.BindingStreamConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * stream工具类
 *
 * @author zxh
 * @date 2021-05-29 17:11
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Component
@Slf4j
@Async
public class BindingComponent {
    private static final String TTL_KEY = "x-message-ttl";
    private final StreamBridge streamBridge;

    /**
     * 发送消息并设置未消费过期时间
     *
     * @param bindingName ${@link String} bind名称
     * @param t           ${@link Object} 数据
     * @param ttl         ${@link Long} 过期时间,单位:s
     * @author zxh
     * @date 2021-07-29 17:52
     */
    public <T> void out(String bindingName, T t, long ttl) {
        MessageBuilder<T> messageBuilder = MessageBuilder.withPayload(t);
        if (ttl > 0) {
            messageBuilder.setHeader(TTL_KEY, ttl);
        }
        Message<T> stringMessage = messageBuilder.build();
        log.debug("------------StreamUtil------发送消息------>out:{}", stringMessage);
        streamBridge.send(bindingName + BindingStreamConstant.OUT_SUFFIX, stringMessage);
    }


    /**
     * 发送消息
     *
     * @param bindingName ${@link String} bind名称
     * @param t           ${@link Object} 数据
     * @author zxh
     * @date 2021-08-30 15:32
     */
    public <T> void out(String bindingName, T t) {
        out(bindingName, t, 0);
    }
}
