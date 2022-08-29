// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构角色-菜单表(RbacOrgRoleMenu)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
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
