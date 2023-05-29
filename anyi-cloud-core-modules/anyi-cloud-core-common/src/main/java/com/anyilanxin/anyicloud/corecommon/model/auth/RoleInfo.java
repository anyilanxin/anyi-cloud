

package com.anyilanxin.anyicloud.corecommon.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Set;

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
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = 4071105391782937997L;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "superRole", title = "是否为超级管理员角色")
    private boolean superRole;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "dataAuthType", title = "数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,6-仅自己")
    private Integer dataAuthType;

    @Schema(name = "roleType", title = "角色类型:0-普通角色，1-机构角色")
    private Integer roleType;

    @Schema(name = "customDataAuthData", title = "自定义数据权限")
    private Set<String> customDataAuthData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleInfo)) return false;

        RoleInfo roleInfo = (RoleInfo) o;

        return getRoleCode() != null ? getRoleCode().equals(roleInfo.getRoleCode()) : roleInfo.getRoleCode() == null;
    }


    @Override
    public int hashCode() {
        return getRoleCode() != null ? getRoleCode().hashCode() : 0;
    }
}
