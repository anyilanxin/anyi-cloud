// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

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
 * 实名信息表条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacUserIdentityQueryVo implements Serializable {
    private static final long serialVersionUID = 492479622107385732L;

    @Schema(name = "identityId", title = "实名信息id")
    private String identityId;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "nationality", title = "名族")
    private String nationality;

    @Schema(name = "idCard", title = "身份证件号码")
    private String idCard;

    @Schema(name = "idCardIssue", title = "身份证件发证机关")
    private String idCardIssue;

    @Schema(name = "idCardEffective", title = "身份证书有效期开始", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime idCardEffective;

    @Schema(name = "idCardEffectiveEnd", title = "身份证有效期结束", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime idCardEffectiveEnd;

    @Schema(name = "positivePhoto", title = "正面照")
    private String positivePhoto;

    @Schema(name = "backPhoto", title = "反面照")
    private String backPhoto;

    @Schema(name = "handheldPhoto", title = "证件手持照")
    private String handheldPhoto;

    @Schema(name = "identityStatus", title = "实名状态:0-待提交,1-审核中，2-未通过(审核失败)，3-通过(审核成功),默认0")
    private Integer identityStatus;

    @Schema(name = "auditStartTime", title = "审核开始时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime auditStartTime;

    @Schema(name = "auditEndTime", title = "审核结束时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime auditEndTime;

    @Schema(name = "bankCardPositive", title = "银行卡正面")
    private String bankCardPositive;

    @Schema(name = "bankCardBack", title = "银行卡反面")
    private String bankCardBack;

    @Schema(name = "bankCardNum", title = "银行卡号")
    private String bankCardNum;

    @Schema(name = "bankReservePhone", title = "银行预留手机号码")
    private String bankReservePhone;

    @Schema(name = "belongArea", title = "银行卡归属地")
    private String belongArea;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

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

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}
