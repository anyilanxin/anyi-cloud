// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
