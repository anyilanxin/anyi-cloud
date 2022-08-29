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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 流程实例删除
 *
 * @author zxiaozhou
 * @date 2020-10-20 10:01
 * @since JDK11
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class DeleteProcessInstanceVo extends ProcessInstanceBatchVo implements Serializable {
    private static final long serialVersionUID = -2625200294039929340L;

    @Schema(name = "reason", title = "删除原因", required = true)
    @NotBlank(message = "删除原因")
    private String reason;

    @Schema(name = "skipCustomListeners", title = "是否跳过自定义监听器,默认true")
    private boolean skipCustomListeners;

    @Schema(name = "externallyTerminated", title = "是否跳过外部的跳转,默认true")
    private boolean externallyTerminated;

    @Schema(name = "skipSubprocesses", title = "是否跳过子流程,默认false")
    private boolean skipSubprocesses;
}
