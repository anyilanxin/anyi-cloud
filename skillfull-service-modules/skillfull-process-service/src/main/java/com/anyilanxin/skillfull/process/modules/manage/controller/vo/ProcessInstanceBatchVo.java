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

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 流程实例批量操作vo
 *
 * @author zxiaozhou
 * @date 2020-10-20 10:02
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class ProcessInstanceBatchVo implements Serializable {
    private static final long serialVersionUID = -8339136439559724835L;

    @Schema(name = "processInstanceIds", title = "流程实例ids", required = true)
    @NotNullSize(message = "流程实例id不能为空")
    private List<String> processInstanceIds;
}
