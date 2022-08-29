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
import java.util.Map;

/**
 * 创建流程任务
 *
 * @author zxiaozhou
 * @date 2020-10-15 20:29
 * @since JDK11
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
