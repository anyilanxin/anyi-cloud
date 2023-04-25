package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程表单详细添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:17:15
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DesignFormDetailVo implements Serializable {
    private static final long serialVersionUID = 328521630698311220L;

    @Schema(name = "fromFiledName", title = "表单字段名称", required = true)
    @NotBlankOrNull(message = "表单字段名称不能为空")
    private String fromFiledName;

    @Schema(name = "formFiledType", title = "表单字段类型:0-字符串,1-数字,2-日期,3-时间。默认0", required = true)
    @NotBlankOrNull(message = "表单字段类型:0-字符串,1-数字,2-日期,3-时间。默认0不能为空")
    private Integer formFiledType;

    @Schema(name = "formFiledLabel", title = "表单字段描述", required = true)
    @NotBlankOrNull(message = "表单字段描述不能为空")
    private String formFiledLabel;

    @Schema(name = "formFiledMust", title = "表单字段是否必填:0-非必填,1-必填。默认0", required = true)
    @NotBlankOrNull(message = "表单字段是否必填:0-非必填,1-必填。默认0不能为空")
    private Integer formFiledMust;

    @Schema(name = "formFiledErrorMsg", title = "表单字段必填验证错误提示信息(当表单字段必填时有效)", required = true)
    @NotBlankOrNull(message = "表单字段必填验证错误提示信息(当表单字段必填时有效)不能为空")
    private String formFiledErrorMsg;

    @Schema(name = "formFiledSort", title = "表单字段排序,越小越靠前。默认0", required = true)
    @NotBlankOrNull(message = "表单字段排序,越小越靠前。默认0不能为空")
    private Integer formFiledSort;

    @Schema(name = "formFiledState", title = "表单字段状态:0-禁用,1-启用。默认0", required = true)
    @NotBlankOrNull(message = "表单字段状态:0-禁用,1-启用。默认0不能为空")
    private Integer formFiledState;

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
