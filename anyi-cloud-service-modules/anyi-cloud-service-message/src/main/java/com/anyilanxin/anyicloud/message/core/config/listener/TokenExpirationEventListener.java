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

package com.anyilanxin.anyicloud.message.core.config.listener;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.anyicloud.coreredis.listener.RedisKeyExpirationEventMessageListener;
import com.anyilanxin.anyicloud.coreredis.utils.SendRedisMsgUtils;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.anyicloud.messagerpc.model.AuthMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;

import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.Nullable;

/**
 * 权限信息被删除监听
 *
 * @author zxh
 * @date 2021-07-09 22:02
 * @since 1.0.0
 */
@Slf4j
public class TokenExpirationEventListener extends RedisKeyExpirationEventMessageListener {

    public TokenExpirationEventListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        log.debug("------------------------>onMessage:\n{}", key);
        if (StringUtils.isNotBlank(key) && key.startsWith(CoreCommonCacheConstant.AUTH_PREFIX + "auth:") && !super.serviceLock(key)) {
            // 广播集群再转给socket处理
            SubscribeMsgModel subscribeMsgModel = new SubscribeMsgModel(SocketMessageEventType.AUTH_EVENT);
            AuthMsgModel model = new AuthMsgModel();
            model.setToken(key.replaceFirst(CoreCommonCacheConstant.AUTH_PREFIX + "auth:", ""));
            model.setType(Status.TOKEN_EXPIRED);
            model.setMessage(Status.ERROR.getMessage());
            subscribeMsgModel.setData(model);
            SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE, JSONObject.toJSONString(subscribeMsgModel, JSONWriter.Feature.WriteMapNullValue));
        }
    }
}
