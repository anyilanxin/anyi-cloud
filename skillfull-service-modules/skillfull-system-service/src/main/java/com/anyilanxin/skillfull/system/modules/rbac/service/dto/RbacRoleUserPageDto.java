package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-客户端分页查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacRoleUserPageDto implements Serializable {
    private static final long serialVersionUID = -49953174853649800L;

    @Schema(name = "roleUserId", title = "角色用户id")
    private String roleUserId;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "userId", title = "用户id")
    private String userId;

}
