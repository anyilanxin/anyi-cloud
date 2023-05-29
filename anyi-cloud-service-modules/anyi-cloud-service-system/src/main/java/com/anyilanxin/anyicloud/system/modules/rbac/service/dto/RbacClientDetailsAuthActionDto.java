

package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 客户端非角色鉴权指令
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacClientDetailsAuthActionDto implements Serializable {
    private static final long serialVersionUID = 735495266631499851L;

    @Schema(name = "apiId", title = "资源api id")
    private String apiId;

    @Schema(name = "resourceId", title = "资源id")
    private String resourceId;

    @Schema(name = "resourceCode", title = "资源编码(即服务编码)")
    private String resourceCode;

    @Schema(name = "permissionAction", title = "非角色鉴权指令")
    private String permissionAction;
}
