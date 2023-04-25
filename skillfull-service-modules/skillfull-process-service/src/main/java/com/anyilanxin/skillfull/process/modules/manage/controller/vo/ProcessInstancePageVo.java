package com.anyilanxin.skillfull.process.modules.manage.controller.vo;

import com.anyilanxin.skillfull.process.core.base.controller.vo.CamundaDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 流程实例删除
 *
 * @author zxiaozhou
 * @date 2020-10-20 10:01
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class ProcessInstancePageVo extends CamundaDateBasePageVo implements Serializable {
    private static final long serialVersionUID = -2625200294039929340L;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "suspended", title = "是否挂起")
    private Boolean suspended;
}
