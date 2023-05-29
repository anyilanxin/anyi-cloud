

package com.anyilanxin.anyicloud.systemrpc.model;

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
public class UserRoleModel implements Serializable {
    private static final long serialVersionUID = 4071105391782937997L;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleModel)) {
            return false;
        }
        UserRoleModel role = (UserRoleModel) o;
        return Objects.equals(getRoleCode(), role.getRoleCode());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getRoleCode());
    }
}
