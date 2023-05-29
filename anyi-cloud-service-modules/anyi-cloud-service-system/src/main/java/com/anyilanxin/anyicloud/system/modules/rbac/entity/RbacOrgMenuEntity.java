

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构-菜单表(RbacOrgMenu)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_org_menu")
public class RbacOrgMenuEntity implements Serializable {
    private static final long serialVersionUID = 927811536662243190L;

    @TableId
    private String roleMenuId;

    /**
     * 权限id
     */
    private String menuId;

    /**
     * 机构id
     */
    private String orgId;
}
