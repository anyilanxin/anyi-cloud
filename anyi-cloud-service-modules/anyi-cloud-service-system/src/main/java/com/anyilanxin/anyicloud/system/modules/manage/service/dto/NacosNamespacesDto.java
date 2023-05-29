

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author zxh zxiaozhou
 * @date 2020-10-11 21:08
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class NacosNamespacesDto implements Serializable {

    private static final long serialVersionUID = -625547796053966768L;

    @Schema(name = "namespace", title = "命名空间ID")
    private String namespace;

    @Schema(name = "namespaceShowName", title = "命名空间名称")
    private String namespaceShowName;

    @Schema(name = "quota", title = "quota")
    private Integer quota;

    @Schema(name = "configCount", title = "配置数")
    private Integer configCount;

    @Schema(name = "type", title = "type")
    private Integer type;
}
