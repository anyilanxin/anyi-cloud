

package com.anyilanxin.anyicloud.process.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.impl.persistence.entity.TenantEntity;

/**
 * 租户信息
 *
 * @author zxh
 * @date 2021-11-05 17:49
 * @since 1.0.0
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
            tenantModel = TenantDto.builder().tenantId(tenantEntity.getId()).name(tenantEntity.getName()).build();
        }
        return tenantModel;
    }
}
