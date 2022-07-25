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
 * 聊天会话信息(ChatSessionInfo)Entity
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
@TableName("msg_chat_session_info")
public class ChatSessionInfoEntity extends BaseEntity {
    private static final long serialVersionUID = -77878933423916780L;

    @TableId
    private String sessionInfoId;

    /**
     * 聊天类型:1-单聊，2-群聊
     */
    private Integer chatType;

    /**
     * 聊天业务id,单聊时为好友会话id,群聊时为群id
     */
    private String chatBusinessId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 未读消息数量
     */
    private Integer noReadMsgNum;

    /**
     * 最近接收消息时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentReceiveMsgTime;

    /**
     * 已读消息数量
     */
    private Integer readMsgNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 接收id,单例为用户id,聊为群id
     */
    private String receiverId;
}
