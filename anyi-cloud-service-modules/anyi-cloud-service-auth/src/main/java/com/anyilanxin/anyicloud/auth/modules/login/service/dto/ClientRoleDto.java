

package com.anyilanxin.anyicloud.auth.modules.login.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色信息
 *
 * @author zxh
 * @date 2021-06-04 00:15
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class ClientRoleDto implements Serializable {
    private static final long serialVersionUID = 4071105391782937997L;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "clientDetailId", title = "客户端主键id")
    private String clientDetailId;

    @Schema(name = "clientId", title = "客户端id")
    private String clientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientRoleDto)) {
            return false;
        }
        ClientRoleDto role = (ClientRoleDto) o;
        return Objects.equals(getRoleCode(), role.getRoleCode());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getRoleCode());
    }
}
