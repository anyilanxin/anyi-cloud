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
import java.util.List;

/**
 * 用户授权
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
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
