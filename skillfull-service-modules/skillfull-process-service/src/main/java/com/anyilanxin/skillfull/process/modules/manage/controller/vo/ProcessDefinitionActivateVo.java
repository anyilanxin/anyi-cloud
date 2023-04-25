package com.anyilanxin.skillfull.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 流程定义激活操作vo
 *
 * @author zxiaozhou
 * @date 2020-10-20 10:02
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessDefinitionActivateVo implements Serializable {
    private static final long serialVersionUID = -8339136439559724835L;

    @Schema(name = "processDefinitionId", title = "流程定义id", required = true)
    @NotBlank(message = "流程定义id不能为空")
    private String processDefinitionId;

    @Schema(name = "activateProcessInstances", title = "是否激活流程实例,默认false")
    private boolean activateProcessInstances;

}
