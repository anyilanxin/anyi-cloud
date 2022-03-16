// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 角色-权限表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:29:14
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacRolePermissionVo implements Serializable {
    private static final long serialVersionUID = 766404491615690831L;

    @Schema(name = "roleId", title = "角色id", required = true)
    @NotBlankOrNull(message = "角色id不能为空")
    private String roleId;

    @Schema(name = "permissionIds", title = "权限信息")
    private List<String> permissionIds;

}