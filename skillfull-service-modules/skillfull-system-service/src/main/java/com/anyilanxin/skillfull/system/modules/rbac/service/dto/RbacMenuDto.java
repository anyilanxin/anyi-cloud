// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 菜单表查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-03 00:29:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacMenuDto implements Serializable {
    private static final long serialVersionUID = 228959270414065220L;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

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

    @Schema(name = "menuType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType")
    private Integer menuType;

    @Schema(name = "iframe", title = "是否外连接,实际为boolean")
    private boolean iframe;

    @Schema(name = "iframeType", title = "外连接类型:0-内嵌,1-外链接")
    private Integer iframeType;

    @Schema(name = "menuStatus", title = "菜单状态:0-禁用,1-启用")
    private Integer menuStatus;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "ignoreAuth", title = "是否忽略权限，只在权限模式为Role的时候有效,实际为boolean")
    private boolean ignoreAuth;

    @Schema(name = "ignoreKeepAlive", title = "是否忽略KeepAlive缓存,实际为boolean")
    private boolean ignoreKeepAlive;

    @Schema(name = "affix", title = "是否固定标签,实际为boolean")
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

    @Schema(name = "showTag", title = "显示tag,0-不显示,1-显示，实际为boolean")
    private boolean showTag;

    @Schema(name = "type", title = "tag类型：primary、error、warn、success")
    private String type;

    @Schema(name = "content", title = "tag内容")
    private String content;

    @Schema(name = "menuSysCode", title = " 系统内置编码(系统自动生成)")
    private String menuSysCode;

    @Schema(name = "dot", title = "是否圆点,默认不是,实际为boolean")
    private boolean dot;

    @Schema(name = "hidePathForChildren", title = "是否在子级菜单的完整path中忽略本级path,实际为boolean")
    private boolean hidePathForChildren;

    @Schema(name = "buttonAction", title = "按钮鉴权指令")
    private String buttonAction;

    @Schema(name = "systemId", title = "所属系统")
    private String systemId;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;
}
