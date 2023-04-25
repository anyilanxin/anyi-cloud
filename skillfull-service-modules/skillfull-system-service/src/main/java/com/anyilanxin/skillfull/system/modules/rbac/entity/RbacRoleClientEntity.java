package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-客户端(RbacRoleClient)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
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
