// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-客户端添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacRoleClientVo implements Serializable {
    private static final long serialVersionUID = -98392153236160668L;

    @Schema(name = "roleId", title = "角色id", required = true)
    @NotBlankOrNull(message = "角色id不能为空")
    private String roleId;

    @Schema(name = "clientDetailId", title = "客户端信息id", required = true)
    @NotBlankOrNull(message = "客户端信息id不能为空")
    private String clientDetailId;

}
