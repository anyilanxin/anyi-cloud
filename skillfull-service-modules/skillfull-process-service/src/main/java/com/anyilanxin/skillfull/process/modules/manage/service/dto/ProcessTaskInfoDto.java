// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import com.anyilanxin.skillfull.process.extend.model.UserTaskPropertyModel;
import com.anyilanxin.skillfull.processapi.model.ProcessFormModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 流程图用户任务信息
 *
 * @author zxiaozhou
 * @date 2022-01-03 10:58
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class ProcessTaskInfoDto implements Serializable, Comparable<ProcessTaskInfoDto> {
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "taskDefinitionKey", title = "任务定义id")
    protected String taskDefinitionKey;

    @Schema(name = "name", title = "任务名称")
    protected String name;

    @Schema(name = "description", title = "任务描述")
    protected String description;

    @Schema(name = "assignee", title = "审批人")
    protected String assignee;

    @Schema(name = "propertyModel", title = "节点信息扩展属性信息")
    private UserTaskPropertyModel propertyModel;

    @Schema(name = "formModel", title = "任务表单")
    protected ProcessFormModel formModel;

    @Schema(name = "formKey", title = "任务表单key")
    private String formKey;

    @Schema(name = "formRef", title = "任务表单ref")
    private String formRef;

    @Schema(name = "dueDateStr", title = "到期日期")
    protected String dueDateStr;

    @Schema(name = "dueFormatDate", title = "到期日期格式化值")
    protected String dueFormatDate;

    @Schema(name = "followUpDateStr", title = "跟进日期")
    protected String followUpDateStr;

    @Schema(name = "followUpFormatDate", title = "跟进时间格式化值")
    protected String followUpFormatDate;

    @Schema(name = "taskSort", title = "任务排序值")
    private String taskSort;

    @Schema(name = "priority", title = "优先级")
    protected int priority;

    @Schema(name = "multiInstance", title = "是否多实例")
    private boolean multiInstance;

    @Schema(name = "sequential", title = "是否顺序")
    private boolean sequential;

    @Override
    public int compareTo(ProcessTaskInfoDto o) {
        String nowTaskSort = this.taskSort;
        if (StringUtils.isBlank(nowTaskSort)) {
            nowTaskSort = StringUtils.isNotBlank(this.taskDefinitionKey) ? this.taskDefinitionKey : "";
        }
        String currentTaskSort = o.taskSort;
        if (StringUtils.isBlank(currentTaskSort)) {
            currentTaskSort = StringUtils.isNotBlank(o.taskDefinitionKey) ? o.taskDefinitionKey : "";
        }
        return nowTaskSort.compareTo(currentTaskSort);
    }
}
