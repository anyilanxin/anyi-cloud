/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.message.modules.websocket.model.chat;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 聊天消息实体
 *
 * @author zxiaozhou
 * @date 2022-03-29 17:02
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Slf4j
public class ChatMsgModel implements Serializable {
    private static final long serialVersionUID = -2454354918168403084L;

    @Schema(name = "msgType", title = "消息类型：1-文字，2-图片，3-文件，4-表情")
    private Integer msgType;

    @Schema(name = "chatType", title = "聊天类型:1-单聊，2-群聊")
    private Integer chatType;

    @Schema(name = "sendMsgContent", title = "消息内容")
    @NotBlankOrNull(message = "消息内容不能为空")
    private String sendMsgContent;

    @Schema(name = "sendUserId", title = "消息发送人id")
    private String sendUserId;

    @Schema(name = "msgSendTime", title = "消息发送时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime msgSendTime;

    @Schema(name = "chatBusinessId", title = "聊天业务id,单聊时为好友会话id,群聊时为群id")
    private String chatBusinessId;

    @Schema(name = "receiverId", title = "接收id,单例为用户id,聊为群id")
    private String receiverId;

    @Schema(name = "type", title = "类型：1-发送消息回复，2-新消息")
    private Integer type;
}
