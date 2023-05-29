

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 创建流程任务
 *
 * @author zxh
 * @date 2020-10-15 20:29
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class StartTaskVo implements Serializable {
    private static final long serialVersionUID = -1645521148889803833L;

    @Schema(name = "formVariables", title = "流程表单数据")
    private Map<String, Object> formVariables;

    @Schema(name = "processInstanceId", title = "流程实例id", required = true)
    @NotBlank(message = "流程实例id不能为空")
    private String processInstanceId;

    @Schema(name = "businessKey", title = "业务id", required = true)
    @NotBlank(message = "业务id不能为空")
    String businessKey;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    private String userId;
}
