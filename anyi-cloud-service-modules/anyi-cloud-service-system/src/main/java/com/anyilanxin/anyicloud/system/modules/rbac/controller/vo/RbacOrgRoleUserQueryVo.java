

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 机构角色-用户条件查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgRoleUserQueryVo implements Serializable {
    private static final long serialVersionUID = 757707144573448258L;

    @Schema(name = "roleUserId", title = "角色用户id")
    private String roleUserId;

    @Schema(name = "orgRoleId", title = "机构角色id")
    private String orgRoleId;

    @Schema(name = "userId", title = "用户id")
    private String userId;
}
