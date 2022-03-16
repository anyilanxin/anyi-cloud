// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 权限数据填值规则表(RbacPermissionDataRule)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:25:58
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
@TableName("auth_rbac_permission_data_rule")
public class RbacPermissionDataRuleEntity extends BaseEntity {
    private static final long serialVersionUID = 252773593576614992L;

    @TableId
    private String permissionDataRuleId;

    @Schema(name = "correlationId", title = "关联业务id:角色id或按钮权限id")
    private String correlationId;

    @Schema(name = "ruleName", title = "规则名称")
    private String ruleName;

    @Schema(name = "ruleColumn", title = "字段")
    private String ruleColumn;

    @Schema(name = "ruleConditions", title = "条件")
    private String ruleConditions;

    @Schema(name = "ruleValue", title = "规则值")
    private String ruleValue;

    @Schema(name = "ruleStatus", title = "规则状态:0-无效，1-有效。默认0")
    private Integer ruleStatus;

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