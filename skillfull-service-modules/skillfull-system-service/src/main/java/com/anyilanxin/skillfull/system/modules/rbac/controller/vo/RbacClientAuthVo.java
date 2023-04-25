package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * 角色权限添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:39:45
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacClientAuthVo implements Serializable {
    private static final long serialVersionUID = 963678539662691043L;

    @Schema(name = "clientResourceApiIds", title = "资源信息")
    private Set<String> clientResourceApiIds;

    @Schema(name = "roleIds", title = "角色信息")
    private Set<String> roleIds;
}
