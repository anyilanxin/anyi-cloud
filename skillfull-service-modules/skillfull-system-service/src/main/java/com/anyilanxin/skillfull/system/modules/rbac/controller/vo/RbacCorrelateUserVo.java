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
 * 用户-关联关系表添加或修改Request
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
public class RbacCorrelateUserVo implements Serializable {
    private static final long serialVersionUID = -49011627519158475L;

    @Schema(name = "correlateId", title = "关联id", required = true)
    @NotBlankOrNull(message = "关联id不能为空")
    private String correlateId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构,2-职位,3-用户组", required = true)
    @NotBlankOrNull(message = "关联类型：1-组织机构,2-职位,3-用户组不能为空")
    private Integer correlateType;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlankOrNull(message = "用户id不能为空")
    private String userId;

}
