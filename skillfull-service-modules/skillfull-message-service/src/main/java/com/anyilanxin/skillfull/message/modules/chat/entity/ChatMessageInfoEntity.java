// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 聊天消息(ChatMessageInfo)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 11:49:29
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@TableName("msg_chat_message_info")
public class ChatMessageInfoEntity extends BaseEntity {
    private static final long serialVersionUID = -90146969860545981L;

    @TableId
    private String chatMessageId;

    /**
     * at用户id，群聊时存在
     */
    private String atUserId;

    /**
     * at类型:1-指定人,2-所有，群聊时存在
     */
    private Integer atType;

    /**
     * 消息类型：1-文字，2-图片，3-文件，4-表情
     */
    private Integer msgType;

    /**
     * 聊天类型:1-单聊，2-群聊
     */
    private Integer chatType;

    /**
     * 消息内容
     */
    private String sendMsgContent;

    /**
     * 消息发送人id
     */
    private String sendUserId;

    /**
     * 消息发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime msgSendTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 聊天业务id,单聊时为好友会话id,群聊时为群id
     */
    private String chatBusinessId;
}
