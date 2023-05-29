

package com.anyilanxin.anyicloud.message.modules.websocket.model.chat;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 聊天消息实体
 *
 * @author zxh
 * @date 2022-03-29 17:02
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Slf4j
public class ChatSendMsgModel implements Serializable {
    private static final long serialVersionUID = -2454354918168403084L;

    @Schema(name = "msgType", title = "消息类型：1-文字，2-图片，3-文件，4-表情")
    private Integer msgType;

    @Schema(name = "chatType", title = "聊天类型:1-单聊，2-群聊")
    private Integer chatType;

    @Schema(name = "sendMsgContent", title = "消息内容")
    @NotBlankOrNull(message = "消息内容不能为空")
    private String sendMsgContent;

    @Schema(name = "receiverId", title = "接收id,单例为用户id,聊为群id")
    private String receiverId;
}
