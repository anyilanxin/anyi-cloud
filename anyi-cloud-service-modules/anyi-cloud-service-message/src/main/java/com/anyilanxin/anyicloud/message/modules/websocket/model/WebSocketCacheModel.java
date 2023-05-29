

package com.anyilanxin.anyicloud.message.modules.websocket.model;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.oauth2mvc.utils.UserContextUtils;
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
 * @author zxh
 * @date 2022-03-29 19:10
 * @since 1.0.0
 */
@Getter
@Setter
public class WebSocketCacheModel implements Serializable {
    private static final long serialVersionUID = -6963249109704133425L;
    /**
     * userId为键
     */
    private ConcurrentHashMap<String, Set<WebSocketSessionModel>> socketSessions = new ConcurrentHashMap<>();

    /**
     * token为键,userId为值
     */
    private ConcurrentHashMap<String, String> socketToken = new ConcurrentHashMap<>();

    /**
     * socket信息
     *
     * @author zxh
     * @date 2022-03-21 01:36
     * @since 1.0.0
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

            if (getSessionId() != null ? !getSessionId().equals(that.getSessionId()) : that.getSessionId() != null)
                return false;
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
         * @author zxh
         * @date 2021-01-25 16:10
         */
        public void sendData(Object data) {
            String stringMsg = JSONObject.toJSONString(data);
            this.session.getAsyncRemote().sendText(stringMsg);
            log.info("------------WebSocketSessionModel------发送websocket消息------>sendData:{}", stringMsg);
        }
    }
}
