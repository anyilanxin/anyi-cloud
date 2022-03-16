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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 用户路由信息
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:29:14
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacUserRouterDto implements Serializable {
    private static final long serialVersionUID = -63399996952662860L;

    @Schema(name = "permissionId", title = "权限id")
    private String permissionId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "component", title = "前端组件")
    private String component;

    @Schema(name = "pathName", title = "路由名称")
    private String pathName;

    @Schema(name = "redirect", title = "重定向地址")
    private String redirect;

    @Schema(name = "permissionType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:PermissionType")
    private Integer permissionType;

    @Schema(name = "iframe", title = "是否外连接")
    private boolean iframe;

    @Schema(name = "iframeType", title = "外连接类型:0-内嵌,1-外链接")
    private Integer iframeType;

    @Schema(name = "permissionStatus", title = "权限状态:0-禁用,1-启用")
    private Integer permissionStatus;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "ignoreAuth", title = "是否忽略权限，只在权限模式为Role的时候有效")
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

    @Schema(name = "serviceId", title = "所属服务id")
    private String serviceId;

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

    @Schema(name = "actionMethods", title = "按钮请求方法")
    private String actionMethods;

    @Schema(name = "actionLimitMethod", title = "按钮限制请求方法:0-不限制,1-限制，实际boolean型")
    private boolean actionLimitMethod;

    @Schema(name = "showTag", title = "显示tag,0-不显示,1-显示，具体为boolean类型")
    private boolean showTag;

    @Schema(name = "type", title = "类型：primary、error、warn、success")
    private String type;

    @Schema(name = "content", title = "内容")
    private String content;

    @Schema(name = "dot", title = "是否圆点")
    private Boolean dot;

    @Schema(name = "systemId", title = "所属系统")
    private String systemId;

    @Schema(name = "permissionSysCode", title = "系统内置编码(系统自动生成)")
    private String permissionSysCode;
}