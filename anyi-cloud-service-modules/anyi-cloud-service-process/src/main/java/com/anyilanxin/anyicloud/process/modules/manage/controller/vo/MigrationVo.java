

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 流程定义迁移
 *
 * @author zxh
 * @date 2021-07-26 11:22
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class MigrationVo implements Serializable {
    private static final long serialVersionUID = -8623417618389717556L;

    @Schema(name = "sourceProcessDefinitionId", title = "原流程定义id", required = true)
    @NotBlank(message = "原流程定义id不能为空")
    private String sourceProcessDefinitionId;

    @Schema(name = "targetProcessDefinitionId", title = "目标流程定义id", required = true)
    @NotBlank(message = "目标流程定义id不能为空")
    private String targetProcessDefinitionId;

    @Schema(name = "processInstanceIds", title = "流程实例id,不传则当前流程定义下的所有实例都会迁移")
    private Set<String> processInstanceIds;

    @Schema(name = "skipCustomListener", title = "是否跳过自定义监听器,默认否")
    private boolean skipCustomListener;

    @Schema(name = "autoActivities", title = "是否默认自动映射活动节点(需要迁移前活动节点在迁移后流程中存在),默认否")
    private boolean autoActivities;

    @Schema(name = "activitiesMap", title = "活动节点迁移映射关系，当autoActivities存在时可不填")
    private Map<String, String> activitiesMap;

    @Schema(name = "skipIoMapping", title = "是否跳过io映射器,默认否")
    private boolean skipIoMapping;

    @Schema(name = "async", title = "是否异步,默认否")
    private boolean async;
}
