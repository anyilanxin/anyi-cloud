

package com.anyilanxin.anyicloud.messagerpc.model;

import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMessageEventType;

import java.io.Serializable;

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
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SocketMsgModel implements Serializable {
    private static final long serialVersionUID = -3931714024892969626L;
    /**
     * 消息事件,具体与SocketMessageEventType常量一致
     */
    private String messageEvent;

    /**
     * 消息数据
     */
    private Object data;

    /**
     * 序列id
     */
    private String serialId;

    public SocketMsgModel(SocketMessageEventType eventType) {
        this.messageEvent = eventType.getType();
    }
}
