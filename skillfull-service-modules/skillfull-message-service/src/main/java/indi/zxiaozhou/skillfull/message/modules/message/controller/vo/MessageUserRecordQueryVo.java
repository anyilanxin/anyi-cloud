// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户消息记录条件查询Request
 *
 * @author zxiaozhou
 * @date 2021-01-26 16:48:31
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MessageUserRecordQueryVo implements Serializable {
    private static final long serialVersionUID = 766984209071961214L;

    @Schema(name = "msgId", title = "消息记录id")
    private String msgId;

    @Schema(name = "title", title = "标题")
    private String title;

    @Schema(name = "msgAbstract", title = "摘要")
    private String msgAbstract;

    @Schema(name = "msgContent", title = "内容")
    private String msgContent;

    @Schema(name = "senderUserName", title = "发布人姓名")
    private String senderUserName;

    @Schema(name = "senderUserId", title = "发布人")
    private String senderUserId;

    @Schema(name = "sendTime", title = "发布时间")
    private LocalDateTime sendTime;

    @Schema(name = "readStatus", title = "阅读状态:0-未阅读,1-阅读。默认0")
    private Integer readStatus;

    @Schema(name = "userId", title = "消息用户id")
    private String userId;

    @Schema(name = "msgType", title = "消息类型:0-系统公告,1-代办事项")
    private Integer msgType;

    @Schema(name = "pageUrl", title = "页面url")
    private String pageUrl;

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

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}