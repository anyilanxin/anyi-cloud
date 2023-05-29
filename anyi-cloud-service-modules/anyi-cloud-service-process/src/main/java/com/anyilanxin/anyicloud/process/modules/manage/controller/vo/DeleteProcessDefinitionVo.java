

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 删除流程定义
 *
 * @author zxh
 * @date 2020-10-20 11:02
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class DeleteProcessDefinitionVo implements Serializable {
    private static final long serialVersionUID = -7851649274002412250L;

    @Schema(name = "processDefinitionId", title = "流程定义id", required = true)
    @NotBlank(message = "流程定义id不能为空")
    private String processDefinitionId;

    @Schema(name = "processDefinitionKey", title = "流程定义key", required = true)
    @NotBlank(message = "流程定义key不能为空")
    private String processDefinitionKey;

    @Schema(name = "cascade", title = "是否级联操作，默认false")
    private boolean cascade;

    @Schema(name = "skipCustomListeners", title = "是否跳过自定义监听器，默认false")
    private boolean skipCustomListeners;

    @Schema(name = "skipIoMappings", title = "是否跳过io映射，默认false")
    private boolean skipIoMappings;
}
