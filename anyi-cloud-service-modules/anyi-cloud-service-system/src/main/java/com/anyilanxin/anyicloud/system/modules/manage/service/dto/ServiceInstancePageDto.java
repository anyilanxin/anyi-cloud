

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 实例信息
 *
 * @author zxh
 * @date 2021-12-28 05:52
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ServiceInstancePageDto implements Serializable {
    private static final long serialVersionUID = 4086735209634588642L;

    @Schema(name = "instanceId", title = "实例唯一id")
    private String instanceId;

    @Schema(name = "ip", title = "实例ip")
    private String ip;

    @Schema(name = "port", title = "实例端口")
    private int port;

    @Schema(name = "weight", title = "实例权重")
    @Builder.Default
    private double weight = 1.0D;

    @Schema(name = "healthy", title = "实例健康状态")
    @Builder.Default
    private boolean healthy = false;

    @Schema(name = "enabled", title = "实例是否接受请求")
    @Builder.Default
    private boolean enabled = true;

    @Schema(name = "ephemeral", title = "是否临时实例")
    @Builder.Default
    private boolean ephemeral = true;

    @Schema(name = "clusterName", title = "实例集群信息")
    private String clusterName;

    @Schema(name = "serviceName", title = "实例服务名")
    private String serviceName;

    @Schema(name = "metadata", title = "元数据")
    private Map<String, String> metadata;
}
