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

package com.anyilanxin.anyicloud.corewebflux.listener;

import com.anyilanxin.anyicloud.corewebflux.utils.CoreWebFluxCommonUtils;
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
 * redis数据被修改监听
 *
 * @author zxh
 * @date 2021-07-09 21:45
 * @since 1.0.0
 */
public class RedisKeyUpdateEventMessageListener extends KeyspaceEventMessageListener implements ApplicationEventPublisherAware {
    private static final Topic KEY_EVENT_UPDATE_TOPIC = new PatternTopic("__keyevent@*__:set");

    @Nullable
    private ApplicationEventPublisher publisher;

    public RedisKeyUpdateEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEY_EVENT_UPDATE_TOPIC);
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
     * @author zxh
     * @date 2021-08-19 16:38
     */
    public boolean serviceLock(String key) {
        return CoreWebFluxCommonUtils.createRedisServiceLock(key, 10L);
    }
}
