// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto;

import indi.zxiaozhou.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限表查询Response
 *
 * @author zxiaozhou
 * @date 2020-10-06 23:14:39
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema
public class RbacPermissionTreeDto extends BaseTree<RbacPermissionTreeDto> implements Serializable {
    private static final long serialVersionUID = -21159540080828469L;

    @Schema(name = "permissionId", title = "权限id")
    private String permissionId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "icon", title = "图标")
    private String icon;

    @Schema(name = "iconType", title = "图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)")
    private Integer iconType;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "permissionType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:PermissionType")
    private Integer permissionType;

    @Schema(name = "actionType", title = "按钮权限类型:1-前端型,2-后端型,3-前后端型")
    private Integer actionType;

    @Schema(name = "permissionStatus", title = "权限状态:0-停用，1-启用,默认0")
    private Integer permissionStatus;
}