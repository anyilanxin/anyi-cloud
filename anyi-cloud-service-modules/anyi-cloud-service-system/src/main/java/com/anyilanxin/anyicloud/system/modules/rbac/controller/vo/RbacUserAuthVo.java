

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户授权
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
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacUserAuthVo implements Serializable {
    private static final long serialVersionUID = -71393470696705701L;

    @Schema(name = "userId", title = "用户名", required = true)
    @NotBlankOrNull(message = "用户名不能为空")
    private String userId;

    @Schema(name = "userRoleIds", title = "用户角色id（只有超级管理员才可操作）")
    private List<String> userRoleIds;

    @Schema(name = "orgRoleIds", title = "机构角色（只有选择了机构才可操作）")
    private List<String> orgRoleIds;
}
