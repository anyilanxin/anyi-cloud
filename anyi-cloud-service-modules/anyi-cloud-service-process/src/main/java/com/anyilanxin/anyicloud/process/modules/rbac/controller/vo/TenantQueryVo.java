

package com.anyilanxin.anyicloud.process.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
public class TenantQueryVo implements Serializable {

    private static final long serialVersionUID = 8099676415879237837L;

    @Schema(name = "name", title = "租户名称")
    protected String name;
}
