// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.auth.model;

import com.anyilanxin.skillfull.corecommon.base.model.system.RouteTagModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 路由meta
 *
 * @author zxiaozhou
 * @date 2021-06-04 00:17
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Schema
public class UserRouteMetaModel implements Serializable {
    private static final long serialVersionUID = -3148648474688359998L;

    @Schema(name = "title", title = "路由title")
    private String title;

    @Schema(name = "ignoreAuth", title = "是否忽略权限")
    private boolean ignoreAuth;

    @Schema(name = "ignoreKeepAlive", title = "是否固定标签,实际为boolean,实际为boolean")
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

    @Schema(name = "hideBreadcrumb", title = "隐藏该路由在面包屑上面的显示,实际为boolean")
    private boolean hideBreadcrumb;

    @Schema(name = "carryParam", title = "是否携带参数并在tab上显示,实际为boolean")
    private boolean carryParam;

    @Schema(name = "hideChildrenInMenu", title = "隐藏所有子菜单,实际为boolean")
    private boolean hideChildrenInMenu;

    @Schema(name = "currentActiveMenu", title = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(name = "hideTab", title = "当前路由不再标签页显示,实际为boolean")
    private boolean hideTab;

    @Schema(name = "hideMenu", title = "当前路由不再菜单显示,实际为boolean")
    private boolean hideMenu;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "ignoreRoute", title = "忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由,实际为boolean")
    private boolean ignoreRoute;

    @Schema(name = "hidePathForChildren", title = "是否在子级菜单的完整path中忽略本级path,实际为boolean")
    private boolean hidePathForChildren;

    @Schema(name = "noRoleActionSet", title = "前端授权费角色指令")
    private Set<String> noRoleActionSet;

    @Schema(name = "actionTagExpression", title = "前端按钮权限标识校验表达式映射")
    private Map<String, String> actionTagExpression;

    @Schema(name = "tag", title = "路由tag信息")
    private RouteTagModel tag;
}

