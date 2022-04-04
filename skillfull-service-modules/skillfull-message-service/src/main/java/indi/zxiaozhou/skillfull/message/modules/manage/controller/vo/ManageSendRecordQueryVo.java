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
 * 消息发送记录表条件查询Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:34
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
public class ManageSendRecordQueryVo implements Serializable {
    private static final long serialVersionUID = -33730870450973490L;

    @Schema(name = "esId", title = "消息id")
    private String esId;

    @Schema(name = "esTitle", title = "消息标题")
    private String esTitle;

    @Schema(name = "esType", title = "推送方式:1-微信模板,2-短信,3-邮件,4-系统公告")
    private Integer esType;

    @Schema(name = "esReceiver", title = "接收人")
    private String esReceiver;

    @Schema(name = "esParam", title = "推送所需参数Json格式")
    private String esParam;

    @Schema(name = "esContent", title = "推送内容")
    private String esContent;

    @Schema(name = "esSendTime", title = "推送时间")
    private LocalDateTime esSendTime;

    @Schema(name = "esSendStatus", title = "推送状态:0未推送 1推送成功 2推送失败")
    private String esSendStatus;

    @Schema(name = "esSendNum", title = "发送次数 超过5次不再发送")
    private Integer esSendNum;

    @Schema(name = "esResult", title = "推送失败原因")
    private String esResult;

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