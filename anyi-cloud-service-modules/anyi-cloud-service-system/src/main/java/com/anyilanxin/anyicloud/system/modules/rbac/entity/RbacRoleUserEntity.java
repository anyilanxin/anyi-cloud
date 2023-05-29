

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色-客户端(RbacRoleUser)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_role_user")
public class RbacRoleUserEntity implements Serializable {
    private static final long serialVersionUID = 351096304447036216L;

    @TableId
    private String roleUserId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 用户id
     */
    private String userId;
}
