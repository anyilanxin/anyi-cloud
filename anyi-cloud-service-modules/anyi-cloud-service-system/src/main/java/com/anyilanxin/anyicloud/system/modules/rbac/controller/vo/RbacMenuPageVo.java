

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 菜单表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-03 00:29:05
 * @since 1.0.0
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
