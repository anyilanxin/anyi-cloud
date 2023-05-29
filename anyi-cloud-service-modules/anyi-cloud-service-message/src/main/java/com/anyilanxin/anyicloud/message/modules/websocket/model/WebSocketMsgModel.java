

package com.anyilanxin.anyicloud.message.modules.websocket.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class WebSocketMsgModel implements Serializable {
    private static final long serialVersionUID = 1163026506131558887L;

    @Schema(title = "客户端定义唯一序号")
    private String uniqueId;

    @Schema(title = "socket数据")
    private String data;

    @Schema(name = "eventType", title = "消息业务类型，具体与SocketBusinessType一致")
    private String eventType;

    // public static void main(String[] args) {
    // ChatSendMsgModel msgModel = new ChatSendMsgModel();
    // msgModel.setMsgType(1);
    // msgModel.setChatType(1);
    // msgModel.setSendMsgContent("测试数据");
    // msgModel.setReceiverId("1444190920879161344");
    // WebSocketMsgModel socketMsgModel = new WebSocketMsgModel();
    // socketMsgModel.setBusinessType(StreamSocketBusinessType.CHART_MSG.getType());
    // socketMsgModel.setData(JSON.toJSONString(msgModel));
    // socketMsgModel.setUniqueId("111111");
    // System.out.println(JSON.toJSONString(socketMsgModel));
    // }
}
