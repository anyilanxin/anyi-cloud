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

package com.anyilanxin.skillfull.message.strategy.msgsubscribe.impl;

import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.IMsgSubscribeStrategy;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.mapstruct.SocketMsgModelCopyMap;
import com.anyilanxin.skillfull.messagerpc.constant.SocketMessageEventContent;
import com.anyilanxin.skillfull.messagerpc.model.AuthMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

/**
 * 鉴权消息
 *
 * @author zxiaozhou
 * @date 2022-08-27 15:15
 * @since JDK11
 */
@Component(value = SocketMessageEventContent.AUTH_EVENT)
@Slf4j
@RequiredArgsConstructor
public class MsgSubscribeAuthImpl implements IMsgSubscribeStrategy {
    private final SocketMsgModelCopyMap copyMap;

    @Override
    public void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions) {
        SocketMsgModel socketMsgModel = copyMap.bToA(subscribeMsgModel);
        log.info("------------------收到鉴权消息------>handleMsg:\n{}", socketMsgModel);
        Object data = socketMsgModel.getData();
        if (Objects.nonNull(data)) {
            AuthMsgModel msgModel = (AuthMsgModel) data;
            sessions.forEach((k, v) -> {
                String token = v.getAttributes().get(WebSocketSessionType.TOKEN.getType()).toString();
                if (token.equals(msgModel.getToken())) {
                    CloseStatus closeStatus = new CloseStatus(msgModel.getType().getCode(), msgModel.getType().getMessage());
                    try {
                        v.close(closeStatus);
                    } catch (IOException e) {
                        log.error("------------------发送消息失败------handleMsg--->\n参数:\n{}\n异常消息:\n{}", msgModel, e.getMessage());
                    }
                }
            });
        }
    }
}
