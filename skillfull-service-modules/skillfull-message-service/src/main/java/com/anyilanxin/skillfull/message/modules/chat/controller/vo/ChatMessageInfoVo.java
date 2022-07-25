// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 聊天消息添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:28
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class ChatMessageInfoVo implements Serializable {
    private static final long serialVersionUID = -51821077723534871L;

    @Schema(name = "atUserId", title = "at用户id，群聊时存在")
    private String atUserId;

    @Schema(name = "atType", title = "at类型:1-指定人,2-所有，群聊时存在")
    private Integer atType;

    @Schema(name = "msgType", title = "消息类型：1-文字，2-图片，3-文件，4-表情", required = true)
    @NotBlankOrNull(message = "消息类型：1-文字，2-图片，3-文件，4-表情不能为空")
    private Integer msgType;

    @Schema(name = "chatType", title = "聊天类型:1-单聊，2-群聊", required = true)
    @NotBlankOrNull(message = "聊天类型:1-单聊，2-群聊不能为空")
    private Integer chatType;

    @Schema(name = "sendMsgContent", title = "消息内容", required = true)
    @NotBlankOrNull(message = "消息内容不能为空")
    private String sendMsgContent;

    @Schema(name = "sendUserId", title = "消息发送人id", required = true)
    @NotBlankOrNull(message = "消息发送人id不能为空")
    private String sendUserId;

    @Schema(name = "msgSendTime", title = "消息发送时间", type = "string", example = "2020-11-12 11:23:59", required = true)
    @NotBlankOrNull(message = "消息发送时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime msgSendTime;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59", required = true)
    @NotBlankOrNull(message = "创建时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0", required = true)
    @NotBlankOrNull(message = "删除状态:0-正常,1-已删除,默认0不能为空")
    private Integer delFlag;

    @Schema(name = "chatBusinessId", title = "聊天业务id,单聊时为好友会话id,群聊时为群id", required = true)
    @NotBlankOrNull(message = "聊天业务id,单聊时为好友会话id,群聊时为群id不能为空")
    private String chatBusinessId;

}
