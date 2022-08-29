// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.impl.persistence.entity.TenantEntity;

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
@EqualsAndHashCode
@Schema
public class TenantDto implements Serializable {
    private static final long serialVersionUID = 6059522918670522012L;

    @Schema(name = "tenantId", title = "租户id")
    protected String tenantId;

    @Schema(name = "name", title = "租户名称")
    protected String name;


    public TenantDto getTenant(Tenant tenant) {
        TenantDto tenantModel = null;
        if (Objects.nonNull(tenant)) {
            TenantEntity tenantEntity = (TenantEntity) tenant;
            tenantModel = TenantDto.builder()
                    .tenantId(tenantEntity.getId())
                    .name(tenantEntity.getName())
                    .build();
        }
        return tenantModel;
    }
}
