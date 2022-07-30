// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 菜单表(RbacMenu)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-03 00:29:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_menu")
public class RbacMenuEntity extends BaseEntity {
    private static final long serialVersionUID = 391242347439472730L;

    @TableId
    private String menuId;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 前端组件
     */
    private String component;

    /**
     * 路由名称
     */
    private String pathName;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType
     */
    private Integer menuType;

    /**
     * 是否外连接,实际为boolean
     */
    private boolean iframe;

    /**
     * 外连接类型:0-内嵌,1-外链接
     */
    private Integer iframeType;

    /**
     * 菜单状态:0-禁用,1-启用
     */
    private Integer menuStatus;

    /**
     * 菜单名称
     */
    private String metaTitle;

    /**
     * 是否忽略权限，只在权限模式为Role的时候有效,实际为boolean
     */
    private boolean ignoreAuth;

    /**
     * 是否忽略KeepAlive缓存,实际为boolean
     */
    private boolean ignoreKeepAlive;

    /**
     * 是否固定标签,实际为boolean
     */
    private boolean affix;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)
     */
    private Integer iconType;

    /**
     * 内嵌iframe的地址
     */
    private String frameSrc;

    /**
     * 路由切换的动画名
     */
    private String transitionName;

    /**
     * 隐藏该路由在面包屑上面的显示,实际为boolean
     */
    private boolean hideBreadcrumb;

    /**
     * 是否携带参数并在tab上显示,实际为boolean
     */
    private boolean carryParam;

    /**
     * 隐藏所有子菜单,实际为boolean
     */
    private boolean hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    private String currentActiveMenu;

    /**
     * 当前路由不再标签页显示,实际为boolean
     */
    private boolean hideTab;

    /**
     * 当前路由不再菜单显示,实际为boolean
     */
    private boolean hideMenu;

    /**
     * 菜单排序
     */
    private Integer orderNo;

    /**
     * 忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由,实际为boolean
     */
    private boolean ignoreRoute;

    /**
     * 显示tag,0-不显示,1-显示，实际为boolean
     */
    private boolean showTag;

    /**
     * tag类型：primary、error、warn、success
     */
    private String type;

    /**
     * tag内容
     */
    private String content;

    /**
     * 是否圆点,默认不是,实际为boolean
     */
    private boolean dot;

    /**
     * 是否在子级菜单的完整path中忽略本级path,实际为boolean
     */
    private boolean hidePathForChildren;

    /**
     * 所属系统
     */
    private String systemId;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 系统内置编码(系统自动生成)
     */
    private String menuSysCode;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 按钮鉴权指令
     */
    private String buttonAction;
}
