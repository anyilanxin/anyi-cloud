// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统通告管理条件查询Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ManageAnnouncementQueryVo implements Serializable {
    private static final long serialVersionUID = -38073029370396263L;

    @Schema(name = "anntId", title = "公告id")
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

    @Schema(name = "receiveType", title = "接收对象类型：1-指定用户,2-指定区域,3-指定组织,4-指定角色,5-所有")
    private Integer receiveType;

    @Schema(name = "receiveInfos", title = "接收对象")
    private String receiveInfos;

    @Schema(name = "sendType", title = "发布方式：0-手动,1-自动")
    private Integer sendType;

    @Schema(name = "finalySendTime", title = "最终发布时间")
    private LocalDateTime finalySendTime;

    @Schema(name = "sendTime", title = "发布时间")
    private LocalDateTime sendTime;

    @Schema(name = "cancelTime", title = "撤销时间")
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