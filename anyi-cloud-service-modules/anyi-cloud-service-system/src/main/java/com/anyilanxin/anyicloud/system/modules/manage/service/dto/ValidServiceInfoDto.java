

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由查询Response
 *
 * @author zxh
 * @date 2020-09-12 16:04:47
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ValidServiceInfoDto implements Serializable {
    private static final long serialVersionUID = 550568918804547444L;

    @Schema(name = "label", title = "服务名称")
    private String label;

    @Schema(name = "serviceCode", title = "服务编码")
    private String serviceCode;

    @Schema(name = "value", title = "服务id")
    private String value;

    @Schema(name = "disabled", title = "是否启用")
    private boolean disabled;
}
