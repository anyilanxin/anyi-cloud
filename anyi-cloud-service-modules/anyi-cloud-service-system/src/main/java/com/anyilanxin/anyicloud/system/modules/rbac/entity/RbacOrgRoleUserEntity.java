

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构角色-用户(RbacOrgRoleUser)Entity
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
@TableName("sys_rbac_org_role_user")
public class RbacOrgRoleUserEntity implements Serializable {
    private static final long serialVersionUID = 281597862717908918L;

    @TableId
    private String roleUserId;

    /**
     * 机构角色id
     */
    private String orgRoleId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 机构id
     */
    private String orgId;
}
