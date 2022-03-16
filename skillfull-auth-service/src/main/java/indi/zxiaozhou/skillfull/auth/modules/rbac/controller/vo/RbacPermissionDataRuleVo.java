// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限数据填值规则表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:41:27
 * @since JDK11
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
public class RbacPermissionDataRuleVo implements Serializable {
    private static final long serialVersionUID = 528887218332889337L;

    @Schema(name = "correlationId", title = "关联业务id:角色id或按钮权限id", required = true)
    @NotBlankOrNull(message = "关联业务id:角色id或按钮权限id不能为空")
    private String correlationId;

    @Schema(name = "ruleName", title = "规则名称", required = true)
    @NotBlankOrNull(message = "规则名称不能为空")
    private String ruleName;

    @Schema(name = "ruleColumn", title = "字段", required = true)
    @NotBlankOrNull(message = "字段不能为空")
    private String ruleColumn;

    @Schema(name = "ruleConditions", title = "条件", required = true)
    @NotBlankOrNull(message = "条件不能为空")
    private String ruleConditions;

    @Schema(name = "ruleValue", title = "规则值", required = true)
    @NotBlankOrNull(message = "规则值不能为空")
    private String ruleValue;

    @Schema(name = "ruleStatus", title = "规则状态:0-无效，1-有效。默认0", required = true)
    @NotBlankOrNull(message = "规则状态:0-无效，1-有效。默认0不能为空")
    private Integer ruleStatus;

    @Schema(name = "remark", title = "备注", required = true)
    @NotBlankOrNull(message = "备注不能为空")
    private String remark;

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