// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 流程任务信息
 *
 * @author zxiaozhou
 * @date 2021-08-01 14:51
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProcessSubmitResultModel implements Serializable {
    private static final long serialVersionUID = 4141293311374431672L;

    @Schema(name = "startFormFields", title = "开始表单信息")
    private List<ProcessFieldModel> startFormFields;

    @Schema(name = "startFormKey", title = "开始表单key")
    protected String startFormKey;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "multiInstanceTask", title = "是否多实例任务")
    private boolean multiInstanceTask;

    @Schema(name = "executionId", title = "执行id(多实例时存在)")
    private String executionId;

    @Schema(name = "taskInfoList", title = "流程任务信息(非多实例时只有一条数据)")
    private List<TaskInfo> taskInfoList;


    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskInfo implements Serializable {
        private static final long serialVersionUID = 4141293311374431672L;

        @Schema(name = "taskFormFields", title = "任务表单信息")
        private List<ProcessFieldModel> taskFormFields;

        @Schema(name = "taskFormKey", title = "任务表单key")
        protected String taskFormKey;

        @Schema(name = "taskId", title = "任务id")
        private String taskId;

        @Schema(name = "executionId", title = "执行id")
        private String executionId;

        @Schema(name = "owner", title = "委托办理人")
        protected String owner;

        @Schema(name = "executionId", title = "办理人")
        protected String assignee;

        @Schema(name = "executionId", title = "任务名称")
        protected String name;

        @Schema(name = "executionId", title = "任务定义key")
        protected String taskDefinitionKey;
    }
}
