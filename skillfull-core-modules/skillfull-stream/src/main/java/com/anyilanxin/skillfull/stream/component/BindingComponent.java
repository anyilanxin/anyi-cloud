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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.stream.component;

import com.anyilanxin.skillfull.stream.constant.BindingStreamConstant;
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
 * @author zxiaozhou
 * @date 2021-05-29 17:11
 * @since JDK1.8
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
   * @param t ${@link Object} 数据
   * @param ttl ${@link Long} 过期时间,单位:s
   * @author zxiaozhou
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
   * @param t ${@link Object} 数据
   * @author zxiaozhou
   * @date 2021-08-30 15:32
   */
  public <T> void out(String bindingName, T t) {
    out(bindingName, t, 0);
  }
}
