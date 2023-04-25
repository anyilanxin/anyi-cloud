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

package com.anyilanxin.skillfull.corewebflux.listener;

import com.anyilanxin.skillfull.corewebflux.utils.CoreWebFluxCommonUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.lang.Nullable;

/**
 * redis数据被删除监听
 *
 * @author zxiaozhou
 * @date 2021-07-09 21:45
 * @since JDK1.8
 */
public class RedisKeyDeleteEventMessageListener extends KeyspaceEventMessageListener
    implements ApplicationEventPublisherAware {
  private static final Topic KEY_EVENT_DELETE_TOPIC = new PatternTopic("__keyevent@*__:del");

  @Nullable private ApplicationEventPublisher publisher;

  public RedisKeyDeleteEventMessageListener(RedisMessageListenerContainer listenerContainer) {
    super(listenerContainer);
  }

  @Override
  protected void doRegister(RedisMessageListenerContainer listenerContainer) {
    listenerContainer.addMessageListener(this, KEY_EVENT_DELETE_TOPIC);
  }

  @Override
  protected void doHandleMessage(Message message) {
    this.publishEvent(new RedisKeyExpiredEvent(message.getBody()));
  }

  protected void publishEvent(RedisKeyExpiredEvent event) {
    if (this.publisher != null) {
      this.publisher.publishEvent(event);
    }
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.publisher = applicationEventPublisher;
  }

  /**
   * 获取服务锁(如果锁不存在则自动添加),过期时间10s
   *
   * @param key ${@link String}
   * @author zxiaozhou
   * @date 2021-08-19 16:38
   */
  public boolean serviceLock(String key) {
    return CoreWebFluxCommonUtils.createRedisServiceLock(key, 10L);
  }
}
