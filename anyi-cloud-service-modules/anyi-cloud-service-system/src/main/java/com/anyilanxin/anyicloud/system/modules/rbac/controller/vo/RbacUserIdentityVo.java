

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 实名信息表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacUserIdentityVo implements Serializable {
    private static final long serialVersionUID = -64228363088092951L;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlankOrNull(message = "用户id不能为空")
    private String userId;

    @Schema(name = "realName", title = "真实姓名", required = true)
    @NotBlankOrNull(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0", required = true)
    @NotBlankOrNull(message = "性别:0-默认未知,1-男,2-女,默认0不能为空")
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

    @Schema(name = "identityStatus", title = "审核状态:0-不通过，1-通过", required = true)
    @NotBlankOrNull(message = "审核状态不能为空")
    @Min(value = 0, message = "审核状态只能为0、1")
    @Max(value = 1, message = "审核状态只能为0、1")
    private Integer identityStatus;

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
}
