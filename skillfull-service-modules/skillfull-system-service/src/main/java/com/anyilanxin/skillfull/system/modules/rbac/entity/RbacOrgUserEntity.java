package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构-用户(RbacOrgUser)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_org_user")
public class RbacOrgUserEntity implements Serializable {
    private static final long serialVersionUID = -77623248473339478L;

    @TableId
    private String orgUserId;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 用户id
     */
    private String userId;
}
