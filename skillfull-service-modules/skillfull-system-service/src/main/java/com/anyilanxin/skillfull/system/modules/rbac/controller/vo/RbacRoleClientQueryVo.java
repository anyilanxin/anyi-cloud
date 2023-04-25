package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-客户端条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacRoleClientQueryVo implements Serializable {
    private static final long serialVersionUID = 775829728044241582L;

    @Schema(name = "roleClient", title = "客户端角色id")
    private String roleClient;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "clientDetailId", title = "客户端信息id")
    private String clientDetailId;

}
