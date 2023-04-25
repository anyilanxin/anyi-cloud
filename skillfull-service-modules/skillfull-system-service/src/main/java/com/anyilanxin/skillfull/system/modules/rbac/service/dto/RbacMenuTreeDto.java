package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import com.anyilanxin.skillfull.corecommon.utils.tree.model.BaseTree;
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
public class RbacMenuTreeDto extends BaseTree<RbacMenuTreeDto> implements Serializable {
    private static final long serialVersionUID = -21159540080828469L;

    @Schema(name = "menuId", title = "菜单id")
    private String menuId;

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

    @Schema(name = "menuType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType")
    private Integer menuType;

    @Schema(name = "menuStatus", title = "权限状态:0-停用，1-启用,默认0")
    private Integer menuStatus;
}
