// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户组信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class GroupTenantVo implements Serializable {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "groupId", title = "用户组id", required = true)
    @NotBlank(message = "用户组id不能为空")
    protected String groupId;

    @Schema(name = "tenantIds", title = "租户ids")
    protected Set<String> tenantIds;
}
