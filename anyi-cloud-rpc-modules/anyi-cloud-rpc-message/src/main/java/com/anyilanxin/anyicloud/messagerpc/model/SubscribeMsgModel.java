

package com.anyilanxin.anyicloud.messagerpc.model;

import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMessageEventType;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息实体
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-07 15:19
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SubscribeMsgModel extends SocketMsgModel {
    private static final long serialVersionUID = -3931714024892969626L;

    /**
     * 发送人自定义session id
     */
    private String sendCustomSessionId;

    /**
     * 发送人session id
     */
    private String sendSessionId;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 发送用户信息
     */
    private UserInfo sendUserInfo;

    /**
     * 发送用户信息id
     */
    private String userId;

    /**
     * 发送人token
     */
    private String sendToken;

    public SubscribeMsgModel(SocketMessageEventType eventType) {
        super(eventType);
    }
}
