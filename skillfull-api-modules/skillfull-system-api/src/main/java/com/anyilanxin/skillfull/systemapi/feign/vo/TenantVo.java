// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.systemapi.feign.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * 租户信息
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
@Schema
public class TenantVo implements Serializable {
    private static final long serialVersionUID = -7832815936485684291L;

    @Schema(name = "tenantId", title = "租户id", required = true)
    @NotBlank(message = "租户id不能为空")
    protected String tenantId;

    @Schema(name = "name", title = "租户名称", required = true)
    @NotBlank(message = "租户名称不能为空")
    protected String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantVo)) {
            return false;
        }
        TenantVo tenantVo = (TenantVo) o;
        return Objects.equals(getTenantId(), tenantVo.getTenantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTenantId());
    }
}
