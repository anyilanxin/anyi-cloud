

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构角色-菜单表(RbacOrgRoleMenu)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_org_role_menu")
public class RbacOrgRoleMenuEntity implements Serializable {
    private static final long serialVersionUID = 919375711368394963L;

    @TableId
    private String orgRoleMenuId;

    /**
     * 权限id
     */
    private String menuId;

    /**
     * 机构角色id
     */
    private String orgRoleId;

    /**
     * 机构id
     */
    private String orgId;
}
