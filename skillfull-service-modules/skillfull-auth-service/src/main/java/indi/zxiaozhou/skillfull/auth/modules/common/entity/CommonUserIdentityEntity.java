// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 实名信息表(CommonUserIdentity)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:40:59
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth_common_user_identity")
public class CommonUserIdentityEntity extends BaseEntity {
    private static final long serialVersionUID = -11698175247283982L;

    @TableId
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

    @Schema(name = "idCardEffective", title = "身份证书有效期开始")
    private LocalDateTime idCardEffective;

    @Schema(name = "idCardEffectiveEnd", title = "身份证有效期结束")
    private LocalDateTime idCardEffectiveEnd;

    @Schema(name = "positivePhoto", title = "正面照")
    private String positivePhoto;

    @Schema(name = "backPhoto", title = "反面照")
    private String backPhoto;

    @Schema(name = "handheldPhoto", title = "证件手持照")
    private String handheldPhoto;

    @Schema(name = "identityStatus", title = "实名状态:0-待审核,1-审核中，2-无效(审核失败)，3-有效(审核成功),默认0")
    private Integer identityStatus;

    @Schema(name = "auditStartTime", title = "审核开始时间")
    private LocalDateTime auditStartTime;

    @Schema(name = "auditEndTime", title = "审核结束时间")
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
}