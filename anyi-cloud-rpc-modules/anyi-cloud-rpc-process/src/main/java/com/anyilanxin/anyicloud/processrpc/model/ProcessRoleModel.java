

package com.anyilanxin.anyicloud.processrpc.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色表查询Response
 *
 * @author zxh
 * @date 2020-10-08 13:44:02
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ProcessRoleModel implements Serializable {
    private static final long serialVersionUID = -17389117269525927L;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "roleSysCode", title = "角色系统编码(系统自动创建)")
    private String roleSysCode;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "parentRoleId", title = "上级角色id")
    private String parentRoleId;
}
