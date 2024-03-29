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

package com.anyilanxin.anyicloud.oauth2commonmvc.config.listener;

import com.anyilanxin.anyicloud.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.anyicloud.oauth2commonmvc.handle.UrlAuthHandle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * redis订阅监听监听
 *
 * @author zxh
 * @date 2021-01-25 21:20
 * @since 1.0.0
 */
@AutoConfiguration
@RequiredArgsConstructor
public class Oauth2MvcRedisListenerConfig {
    @Value("${spring.application.name:unknown}")
    private String serviceName;

    private final RedisConnectionFactory redisConnectionFactory;
    private final StringRedisTemplate redisTemplate;
    private final UrlAuthHandle urlAuthHandle;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);

        // url动态权限重新加载
        MessageListenerAdapter authUrlReload = new MessageListenerAdapter(urlAuthHandle, "urlAuthReload");
        authUrlReload.setSerializer(redisTemplate.getValueSerializer());
        authUrlReload.afterPropertiesSet();
        redisMessageListenerContainer.addMessageListener(authUrlReload, new PatternTopic(RedisSubscribeConstant.SECURITY_AUTH_URL_RELOAD));

        // url动态权限更新
        MessageListenerAdapter authUrlUpdate = new MessageListenerAdapter(urlAuthHandle, "urlAuthUpdate");
        authUrlUpdate.setSerializer(redisTemplate.getValueSerializer());
        authUrlUpdate.afterPropertiesSet();
        redisMessageListenerContainer.addMessageListener(authUrlUpdate, new PatternTopic(RedisSubscribeConstant.SECURITY_AUTH_URL_UPDATE_PREFIX + serviceName));

        // url动态权限删除
        MessageListenerAdapter authUrlDelete = new MessageListenerAdapter(urlAuthHandle, "urlAuthDelete");
        authUrlDelete.setSerializer(redisTemplate.getValueSerializer());
        authUrlDelete.afterPropertiesSet();
        redisMessageListenerContainer.addMessageListener(authUrlDelete, new PatternTopic(RedisSubscribeConstant.SECURITY_AUTH_URL_DELETE_PREFIX + serviceName));

        return redisMessageListenerContainer;
    }
}
