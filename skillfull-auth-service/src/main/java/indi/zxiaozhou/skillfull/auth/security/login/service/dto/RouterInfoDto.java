// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.dto;

import indi.zxiaozhou.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户菜单信息
 *
 * @author zxiaozhou
 * @date 2020-07-01 03:21
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema
public class RouterInfoDto extends BaseTree<RouterInfoDto> implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;


    @Schema(name = "path", title = "路由id")
    private String path;

    @Schema(name = "name", title = "路由id")
    private String name;

    @Schema(name = "component", title = "路由id")
    private String component;

    @Schema(name = "redirect", title = "路由id")
    private String redirect;

    @Schema(name = "permissionId", title = "权限id")
    private String permissionId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "title", title = "路由title")
    private String title;

    @Schema(name = "ignoreAuth", title = "是否忽略权限")
    private boolean ignoreAuth;

    @Schema(name = "ignoreKeepAlive", title = "是否忽略KeepAlive缓存")
    private boolean ignoreKeepAlive;

    @Schema(name = "affix", title = "是否固定标签")
    private boolean affix;

    @Schema(name = "icon", title = "图标")
    private String icon;

    @Schema(name = "iconType", title = "图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)")
    private Integer iconType;

    @Schema(name = "frameSrc", title = "内嵌iframe的地址")
    private String frameSrc;

    @Schema(name = "transitionName", title = "路由切换的动画名")
    private String transitionName;

    @Schema(name = "hideBreadcrumb", title = "隐藏该路由在面包屑上面的显示")
    private boolean hideBreadcrumb;

    @Schema(name = "carryParam", title = "是否携带参数并在tab上显示")
    private boolean carryParam;

    @Schema(name = "hideChildrenInMenu", title = "隐藏所有子菜单")
    private boolean hideChildrenInMenu;

    @Schema(name = "currentActiveMenu", title = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(name = "hideTab", title = "当前路由不再标签页显示")
    private boolean hideTab;

    @Schema(name = "hideMenu", title = "当前路由不再菜单显示")
    private boolean hideMenu;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "ignoreRoute", title = "忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由")
    private boolean ignoreRoute;

    @Schema(name = "hidePathForChildren", title = "是否在子级菜单的完整path中忽略本级path")
    private boolean hidePathForChildren;

    @Schema(name = "checkStrategy", title = "按钮权限校验方式,来源于常量字典:ACTION_TYPE")
    private String checkStrategy;

    @Schema(name = "actionType", title = "按钮权限类型:1-前端型,2-后端型,3-前后端型")
    private Integer actionType;

    @Schema(name = "actionUri", title = "后端uri")
    private String actionUri;

    @Schema(name = "actions", title = "按钮权限action,多个英文逗号隔开")
    private String actions;
}

