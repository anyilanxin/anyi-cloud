

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色-菜单表(RbacRoleMenu)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_role_menu")
public class RbacRoleMenuEntity implements Serializable {
    private static final long serialVersionUID = -56163933713664502L;

    @TableId
    private String roleMenuId;

    /**
     * 权限id
     */
    private String menuId;

    /**
     * 角色id
     */
    private String roleId;
}
