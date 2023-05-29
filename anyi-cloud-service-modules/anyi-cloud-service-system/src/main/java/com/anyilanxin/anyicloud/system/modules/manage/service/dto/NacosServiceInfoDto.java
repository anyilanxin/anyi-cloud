

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author zxh zxiaozhou
 * @date 2020-10-11 21:04
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class NacosServiceInfoDto implements Serializable {
    private static final long serialVersionUID = 2784324896203301262L;

    @Schema(name = "name", title = "服务名")
    private String name;

    @Schema(name = "groupName", title = "分组名称")
    private String groupName;

    @Schema(name = "clusterCount", title = "集群数目")
    private Integer clusterCount;

    @Schema(name = "ipCount", title = "实例数")
    private Integer ipCount;

    @Schema(name = "healthyInstanceCount", title = "健康实例数")
    private Integer healthyInstanceCount;

    @Schema(name = "triggerFlag", title = "触发保护阈值")
    private Boolean triggerFlag;
}
