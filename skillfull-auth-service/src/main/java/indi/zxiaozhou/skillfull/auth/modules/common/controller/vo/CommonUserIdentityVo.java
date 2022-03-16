// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实名信息表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:41:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonUserIdentityVo implements Serializable {
    private static final long serialVersionUID = 503749592157446756L;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlankOrNull(message = "用户id不能为空")
    private String userId;

    @Schema(name = "realName", title = "真实姓名", required = true)
    @NotBlankOrNull(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0", required = true)
    @NotBlankOrNull(message = "性别:0-默认未知,1-男,2-女,默认0不能为空")
    private Integer sex;

    @Schema(name = "nationality", title = "名族", required = true)
    @NotBlankOrNull(message = "名族不能为空")
    private String nationality;

    @Schema(name = "idCard", title = "身份证件号码", required = true)
    @NotBlankOrNull(message = "身份证件号码不能为空")
    private String idCard;

    @Schema(name = "idCardIssue", title = "身份证件发证机关", required = true)
    @NotBlankOrNull(message = "身份证件发证机关不能为空")
    private String idCardIssue;

    @Schema(name = "idCardEffective", title = "身份证书有效期开始", required = true)
    @NotBlankOrNull(message = "身份证书有效期开始不能为空")
    private LocalDateTime idCardEffective;

    @Schema(name = "idCardEffectiveEnd", title = "身份证有效期结束", required = true)
    @NotBlankOrNull(message = "身份证有效期结束不能为空")
    private LocalDateTime idCardEffectiveEnd;

    @Schema(name = "positivePhoto", title = "正面照", required = true)
    @NotBlankOrNull(message = "正面照不能为空")
    private String positivePhoto;

    @Schema(name = "backPhoto", title = "反面照", required = true)
    @NotBlankOrNull(message = "反面照不能为空")
    private String backPhoto;

    @Schema(name = "handheldPhoto", title = "证件手持照", required = true)
    @NotBlankOrNull(message = "证件手持照不能为空")
    private String handheldPhoto;

    @Schema(name = "identityStatus", title = "实名状态:0-待审核,1-审核中，2-无效(审核失败)，3-有效(审核成功),默认0", required = true)
    @NotBlankOrNull(message = "实名状态:0-待审核,1-审核中，2-无效(审核失败)，3-有效(审核成功),默认0不能为空")
    private Integer identityStatus;

    @Schema(name = "auditStartTime", title = "审核开始时间", required = true)
    @NotBlankOrNull(message = "审核开始时间不能为空")
    private LocalDateTime auditStartTime;

    @Schema(name = "auditEndTime", title = "审核结束时间", required = true)
    @NotBlankOrNull(message = "审核结束时间不能为空")
    private LocalDateTime auditEndTime;

    @Schema(name = "bankCardPositive", title = "银行卡正面", required = true)
    @NotBlankOrNull(message = "银行卡正面不能为空")
    private String bankCardPositive;

    @Schema(name = "bankCardBack", title = "银行卡反面", required = true)
    @NotBlankOrNull(message = "银行卡反面不能为空")
    private String bankCardBack;

    @Schema(name = "bankCardNum", title = "银行卡号", required = true)
    @NotBlankOrNull(message = "银行卡号不能为空")
    private String bankCardNum;

    @Schema(name = "bankReservePhone", title = "银行预留手机号码", required = true)
    @NotBlankOrNull(message = "银行预留手机号码不能为空")
    private String bankReservePhone;

    @Schema(name = "belongArea", title = "银行卡归属地", required = true)
    @NotBlankOrNull(message = "银行卡归属地不能为空")
    private String belongArea;

    @Schema(name = "remark", title = "备注", required = true)
    @NotBlankOrNull(message = "备注不能为空")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码", required = true)
    @NotBlankOrNull(message = "创建区域编码不能为空")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码", required = true)
    @NotBlankOrNull(message = "创建职位编码不能为空")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码", required = true)
    @NotBlankOrNull(message = "创建机构系统编码不能为空")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码", required = true)
    @NotBlankOrNull(message = "创建系统编码不能为空")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id", required = true)
    @NotBlankOrNull(message = "创建租户id不能为空")
    private String createTenantId;

    @Schema(name = "createUserId", title = "创建用户id", required = true)
    @NotBlankOrNull(message = "创建用户id不能为空")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名", required = true)
    @NotBlankOrNull(message = "创建用户姓名不能为空")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", required = true)
    @NotBlankOrNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id", required = true)
    @NotBlankOrNull(message = "更新用户id不能为空")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名", required = true)
    @NotBlankOrNull(message = "更新用户姓名不能为空")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", required = true)
    @NotBlankOrNull(message = "更新时间不能为空")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0", required = true)
    @NotBlankOrNull(message = "删除状态:0-正常,1-已删除,默认0不能为空")
    private Integer delFlag;

}