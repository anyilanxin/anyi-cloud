// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processapi.model;

import com.anyilanxin.skillfull.corecommon.model.stream.SubmitFormModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * 任务完成
 *
 * @author zxiaozhou
 * @date 2020-10-19 19:31
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SubmitParentTaskModel implements Serializable {
    private static final long serialVersionUID = -1542891350803383061L;

    @Schema(name = "taskId", title = "任务id", required = true)
    @NotBlank(message = "任务id不能为空")
    private String taskId;

    @Schema(name = "taskFormData", title = "任务表单数据")
    private Map<String, Object> taskFormData;

    @Schema(name = "taskVariables", title = "需要注入任务变量")
    private Map<String, Object> taskVariables;

    @Schema(name = "processFormData", title = "流程表单数据")
    private Map<String, SubmitFormModel> processFormData;

    @Schema(name = "processVariables", title = "需要注入流程变量")
    private Map<String, Object> processVariables;
}
