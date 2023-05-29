

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色-客户端(RbacRoleClient)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_role_client")
public class RbacRoleClientEntity implements Serializable {
    private static final long serialVersionUID = 761816377397439151L;

    @TableId
    private String roleClient;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 客户端信息id
     */
    private String clientDetailId;
}
