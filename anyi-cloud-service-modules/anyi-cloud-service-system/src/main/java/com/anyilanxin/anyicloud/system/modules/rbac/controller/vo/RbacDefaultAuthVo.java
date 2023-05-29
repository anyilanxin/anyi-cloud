

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 组织表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema
public class RbacDefaultAuthVo extends RbacRoleAuthVo implements Serializable {
    private static final long serialVersionUID = 963678539662691043L;

    @Schema(name = "orgId", title = "组织id", required = true)
    @NotBlank(message = "组织id不能为空")
    private String orgId;
}
