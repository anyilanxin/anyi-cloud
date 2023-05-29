

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 实名信息表(RbacUserIdentity)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_user_identity")
public class RbacUserIdentityEntity extends BaseEntity {
    private static final long serialVersionUID = 863128555244116884L;

    @TableId
    private String identityId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别:0-默认未知,1-男,2-女,默认0
     */
    private Integer sex;

    /**
     * 名族
     */
    private String nationality;

    /**
     * 身份证件号码
     */
    private String idCard;

    /**
     * 身份证件发证机关
     */
    private String idCardIssue;

    /**
     * 身份证书有效期开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime idCardEffective;

    /**
     * 身份证有效期结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime idCardEffectiveEnd;

    /**
     * 正面照
     */
    private String positivePhoto;

    /**
     * 反面照
     */
    private String backPhoto;

    /**
     * 证件手持照
     */
    private String handheldPhoto;

    /**
     * 实名状态:0-待提交,1-审核中，2-未通过(审核失败)，3-通过(审核成功),默认0
     */
    private Integer identityStatus;

    /**
     * 审核开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime auditStartTime;

    /**
     * 审核结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime auditEndTime;

    /**
     * 银行卡正面
     */
    private String bankCardPositive;

    /**
     * 银行卡反面
     */
    private String bankCardBack;

    /**
     * 银行卡号
     */
    private String bankCardNum;

    /**
     * 银行预留手机号码
     */
    private String bankReservePhone;

    /**
     * 银行卡归属地
     */
    private String belongArea;

    /**
     * 备注
     */
    private String remark;
}
