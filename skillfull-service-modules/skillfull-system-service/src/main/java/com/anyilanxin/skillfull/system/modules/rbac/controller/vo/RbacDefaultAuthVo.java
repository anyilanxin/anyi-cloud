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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 组织表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:39:45
 * @since JDK1.8
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
