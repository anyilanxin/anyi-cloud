package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 21:08
 * @since JDK1.8
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
