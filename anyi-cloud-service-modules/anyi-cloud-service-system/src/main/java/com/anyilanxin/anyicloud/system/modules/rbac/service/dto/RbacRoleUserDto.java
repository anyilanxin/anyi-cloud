

package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色-客户端查询Response
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
@Schema
public class RbacRoleUserDto implements Serializable {
    private static final long serialVersionUID = 898756101234870861L;

    @Schema(name = "roleUserId", title = "角色用户id")
    private String roleUserId;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "userId", title = "用户id")
    private String userId;
}
