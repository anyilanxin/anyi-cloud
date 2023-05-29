

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程部署
 *
 * @author zxh
 * @date 2020-10-15 08:44
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class DeploymentHistoryVo implements Serializable {
    private static final long serialVersionUID = 397848532480067008L;

    @Schema(name = "deploymentName", title = "部署名称", required = true)
    @NotBlank(message = "部署名称不能为空")
    private String deploymentName;

    @Schema(name = "historyModelId", title = "历史模型id", required = true)
    @NotBlank(message = "历史模型id不能为空")
    private String historyModelId;

    @Schema(name = "deploymentId", title = "部署id", required = true)
    @NotBlank(message = "原始部署id不能为空")
    private String deploymentId;

    @Schema(name = "activateProcessDate", title = "流程定义激活时间(不指定则立马激活)", example = "2020-12-21 12:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime activateProcessDate;
}
