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

import com.anyilanxin.skillfull.process.core.base.controller.vo.CamundaNoDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 租户信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class TenantQueryPageVoCamunda extends CamundaNoDateBasePageVo {
    private static final long serialVersionUID = -4949394561962888487L;

    @Schema(name = "name", title = "租户名称")
    protected String name;
}
