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

package com.anyilanxin.skillfull.message.strategy.afterconnection.impl;

import com.anyilanxin.skillfull.message.strategy.afterconnection.AfterConnectionStrategy;
import com.anyilanxin.skillfull.message.utils.WsUtils;
import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.skillfull.messagerpc.model.NoticeMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 发送注意通知
 *
 * @author zxiaozhou
 * @date 2022-08-28 21:15
 * @since JDK11
 */
@Component
public class IAfterConnectionSentNoticeImpl implements AfterConnectionStrategy {
  @Override
  public void handleAfterMsg(WebSocketSession session) {
    for (int i = 0; i < 5; i++) {
      SocketMsgModel msgModel = new SocketMsgModel(SocketMessageEventType.NOTICE_EVENT);
      NoticeMsgModel model = new NoticeMsgModel();
      model.setType(1);
      model.setShowType(1);
      model.setData("请维护好环境，别乱删数据！！！！！！！！！有问题进群：666029437；");
      msgModel.setData(model);
      WsUtils.sendMsg(session, msgModel);
    }
  }
}
