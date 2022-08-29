// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 流程部署
 *
 * @author zxiaozhou
 * @date 2020-10-15 08:44
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class DeploymentVo implements Serializable {
    private static final long serialVersionUID = 397848532480067008L;

    @Schema(name = "deploymentName", title = "部署名称", required = true)
    @NotBlank(message = "部署名称不能为空")
    private String deploymentName;

    @Schema(name = "modelId", title = "模型id", required = true)
    @NotBlank(message = "模型id不能为空")
    private String modelId;

    @Schema(name = "activateProcessDate", title = "流程定义激活时间(不指定则立马激活)", example = "2020-12-21 12:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime activateProcessDate;
}
