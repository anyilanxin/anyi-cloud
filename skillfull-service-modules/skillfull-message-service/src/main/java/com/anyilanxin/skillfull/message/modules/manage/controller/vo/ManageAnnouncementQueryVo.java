package com.anyilanxin.skillfull.message.modules.manage.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 系统通告公告管理条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:34:22
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class ManageAnnouncementQueryVo implements Serializable {
    private static final long serialVersionUID = -41744542104992913L;

    @Schema(name = "anntId", title = "通知公告id")
    private String anntId;

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

    @Schema(name = "announcementType", title = "通知公告类型：1-系统公告，2-待办事项通知")
    private Integer announcementType;

    @Schema(name = "receiveUserId", title = "接收用户id")
    private String receiveUserId;

    @Schema(name = "receiveAreaCode", title = "接收区域编码")
    private String receiveAreaCode;

    @Schema(name = "receiveOrgId", title = "接收组织机构id")
    private String receiveOrgId;

    @Schema(name = "receiveOrgCode", title = "接收组织机构编码")
    private String receiveOrgCode;

    @Schema(name = "sendType", title = "发布方式：0-手动,1-自动")
    private Integer sendType;

    @Schema(name = "autoSendTime", title = "自动发布时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime autoSendTime;

    @Schema(name = "sendTime", title = "发布时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    @Schema(name = "cancelTime", title = "撤销时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime cancelTime;

    @Schema(name = "sendStatus", title = "发布状态：0未发布，1已发布，2已撤销，默认0")
    private Integer sendStatus;

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

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
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

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}
