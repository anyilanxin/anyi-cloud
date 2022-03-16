// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.model;


import indi.zxiaozhou.skillfull.auth.security.model.TokenUserInfo;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户所有信息
 *
 * @author zxiaozhou
 * @date 2020-07-01 03:21
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
@Schema(title = "用户权限查询Response", description = "用户权限查询响应")
public class LoginPermissionInfo implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;

    @Schema(name = "roles", title = "用户角色信息")
    private Set<String> roles;

    @Schema(name = "userInfo", title = "用户信息")
    private TokenUserInfo.UserInfo userInfo;


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class Auth implements Serializable {

        private static final long serialVersionUID = -3622631484895811279L;

        @Schema(name = "perms", title = "按钮权限编码")
        private String perms;

        @Schema(name = "url", title = "路径")
        private String url;

        @Schema(name = "describe", title = "按钮权限描述，与即菜单名称公用一个字段")
        private String describe;
    }


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class Meta implements Serializable {
        private static final long serialVersionUID = 7621553578404814024L;
        /**
         * 权限
         */
        private Set<String> roles;
        /**
         * 路由标题
         */
        private String title;
        /**
         * 路由图标
         */
        private String icon;
        /**
         * 是否缓存
         */
        private boolean noCache;
        /**
         * breadcrumb面包屑中显示
         */
        @Builder.Default
        private boolean breadcrumb = true;
        /**
         * 固定在tags-view中
         */
        private boolean affix;
        /**
         * 高亮相对应的侧边栏
         */
        private String activeMenu;
    }


    @Getter
    @Setter
    @ToString(callSuper = true)
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Menu extends BaseTree<Menu> {
        private static final long serialVersionUID = 5348672245782890620L;
        /**
         * id
         */
        private String id;
        /**
         * 父级id
         */
        private String parentId;

        /**
         * 侧边栏出现
         */
        private boolean hidden;
        /**
         * noRedirect:面包屑不可点击
         * redirect:面包屑可点击
         */
        private String redirect;
        /**
         * 是否根路由
         */
        private boolean alwaysShow;
        /**
         * 路由名称
         */
        private String name;

        /**
         * 路由路径
         */
        private String path;
        /**
         * 组件
         */
        private String component;

        /**
         * 菜单meta
         */
        private Meta meta;
    }
}

