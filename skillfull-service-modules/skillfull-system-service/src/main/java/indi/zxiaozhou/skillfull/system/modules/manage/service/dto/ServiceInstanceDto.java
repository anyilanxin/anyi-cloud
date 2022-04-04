// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service.dto;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 服务实例信息
 *
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 13:24
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ServiceInstanceDto implements Serializable {
    private static final long serialVersionUID = 2013577984078828501L;

    @Schema(name = "instanceNum", title = "实例总数")
    private int instanceNum;

    @Schema(name = "healthyNum", title = "健康实例数量")
    private int healthyNum;

    @Schema(name = "unhealthyNum", title = "不健康数量")
    private int unhealthyNum;

    @Schema(name = "enabledNum", title = "能否接收请求实例数量")
    private int enabledNum;

    @Schema(name = "ephemeralNum", title = "临时实例数量")
    private int ephemeralNum;

    @Schema(name = "serviceName", title = "服务名称")
    private String serviceName;

    @Schema(name = "instanceMap", title = "服务实例id,服务实例信息")
    @JsonIgnore
    private Map<String, Instance> instanceMap;

    @Schema(name = "serviceInstanceDetails", title = "服务实例详细")
    private List<ServiceInstanceDetail> serviceInstanceDetails;


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class ServiceInstanceDetail implements Serializable {
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
}
