// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 删除流程定义
 *
 * @author zxiaozhou
 * @date 2020-10-20 11:02
 * @since JDK11
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
