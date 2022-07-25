package com.anyilanxin.skillfull.corecommon.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户登录路由信息
 *
 * @author zxiaozhou
 * @date 2021-07-11 20:23
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserRouteModel implements Serializable, Comparable<UserRouteModel> {
    private static final long serialVersionUID = 8153487858620471860L;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "menuSysCode", title = "系统内置编码(系统自动生成)")
    private String menuSysCode;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "component", title = "前端组件")
    private String component;

    @Schema(name = "name", title = "路由名称")
    private String name;

    @Schema(name = "redirect", title = "重定向地址")
    private String redirect;

    @Schema(name = "meta", title = "路由meta信息")
    private UserRouteMetaModel meta;

    @Override
    public int compareTo(UserRouteModel o) {
        return this.getMeta().getOrderNo().compareTo(o.getMeta().getOrderNo());
    }
}
