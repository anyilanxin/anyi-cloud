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

import indi.zxiaozhou.skillfull.coredatabase.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 权限数据填值规则表分页查询Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:41:27
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacPermissionDataRulePageVo extends BasePageVo {
    private static final long serialVersionUID = -77198574420438422L;

    @Schema(name = "permissionDataRuleId", title = "填值规则id")
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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

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

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}