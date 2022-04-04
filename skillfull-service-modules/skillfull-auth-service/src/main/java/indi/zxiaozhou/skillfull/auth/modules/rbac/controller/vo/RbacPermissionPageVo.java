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

/**
 * 权限表分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-01-14 22:02:24
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
public class RbacPermissionPageVo extends BasePageVo {
    private static final long serialVersionUID = 437441342227962599L;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "permissionStatus", title = "权限状态:0-停用，1-启用,默认0")
    private Integer permissionStatus;

    @Schema(name = "permissionTypes", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:PermissionType")
    private Integer[] permissionTypes;

    @Schema(name = "systemId", title = "所属系统")
    private String systemId;
}