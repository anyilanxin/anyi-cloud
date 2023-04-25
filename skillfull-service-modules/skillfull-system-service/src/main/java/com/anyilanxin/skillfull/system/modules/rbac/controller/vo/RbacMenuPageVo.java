package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 菜单表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-03 00:29:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacMenuPageVo extends BasePageVo {
    private static final long serialVersionUID = 726504318526370236L;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "systemId", title = "系统id")
    private String systemId;

    @Schema(name = "menuTypes", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType")
    private List<Integer> menuTypes;

    @Schema(name = "menuStatus", title = "菜单状态:0-禁用,1-启用")
    private Integer menuStatus;
}
