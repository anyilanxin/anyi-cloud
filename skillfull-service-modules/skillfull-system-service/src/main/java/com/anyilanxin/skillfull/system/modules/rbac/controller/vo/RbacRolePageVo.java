package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 19:29:58
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacRolePageVo extends BasePageVo {
    private static final long serialVersionUID = -42872900253528636L;

    @Schema(name = "keyword", title = "角色名称或角色编码")
    private String keyword;

    @Schema(name = "roleStatus", title = "角色状态:0-禁用,1-启用,默认0")
    private Integer roleStatus;
}
