/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.message.modules.websocket.model;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.oauth2mvc.utils.UserContextUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
* websocket缓存实体
*
* @author zxiaozhou
* @date 2022-03-29 19:10
* @since JDK1.8
*/
@Getter
@Setter
public class WebSocketCacheModel implements Serializable {
    private static final long serialVersionUID = -6963249109704133425L;
    /** userId为键 */
    private ConcurrentHashMap<String, Set<WebSocketSessionModel>> socketSessions =
            new ConcurrentHashMap<>();

    /** token为键,userId为值 */
    private ConcurrentHashMap<String, String> socketToken = new ConcurrentHashMap<>();

    /**
    * socket信息
    *
    * @author zxiaozhou
    * @date 2022-03-21 01:36
    * @since JDK1.8
    */
    @Getter
    @Setter
    @ToString
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @Schema
    @Slf4j
    public static class WebSocketSessionModel implements Serializable {
        private static final long serialVersionUID = 1163026506131558887L;
        private Session session;
        private String sessionId;
        private String userId;
        private String token;

        public WebSocketSessionModel(Session session, String token) {
            this.session = session;
            this.token = token;
            this.sessionId = session.getId();
            this.userId = UserContextUtils.getUserInfo(token).getUserId();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WebSocketSessionModel)) return false;

            WebSocketSessionModel that = (WebSocketSessionModel) o;

            if (getSessionId() != null
                    ? !getSessionId().equals(that.getSessionId())
                    : that.getSessionId() != null) return false;
            return getToken() != null ? getToken().equals(that.getToken()) : that.getToken() == null;
        }

        @Override
        public int hashCode() {
            int result = getSessionId() != null ? getSessionId().hashCode() : 0;
            result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
            return result;
        }

        /**
        * 发送websocket消息
        *
        * @param data ${@link Object} 待发送消息
        * @author zxiaozhou
        * @date 2021-01-25 16:10
        */
        public void sendData(Object data) {
            String stringMsg = JSONObject.toJSONString(data);
            this.session.getAsyncRemote().sendText(stringMsg);
            log.info("------------WebSocketSessionModel------发送websocket消息------>sendData:{}", stringMsg);
        }
    }
}
