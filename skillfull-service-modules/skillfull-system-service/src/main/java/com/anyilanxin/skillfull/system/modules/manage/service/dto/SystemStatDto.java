package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 21:04
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class SystemStatDto implements Serializable {
    private static final long serialVersionUID = 2784324896203301262L;

    @Schema(name = "manageTotalService", title = "纳入管理总服务数")
    private int manageTotalService;

    @Schema(name = "notManageTotalService", title = "未纳入管理总服务数")
    private int notManageTotalService;

    @Schema(name = "healthyInstanceCount", title = "健康实例数")
    private int healthyInstanceCount;

    @Schema(name = "noHealthyInstanceCount", title = "不健康实例数")
    private int noHealthyInstanceCount;
}
