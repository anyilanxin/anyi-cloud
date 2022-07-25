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

import com.anyilanxin.skillfull.process.core.base.controller.vo.CamundaDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 流程定义查询
 *
 * @author zxiaozhou
 * @date 2020-10-14 20:51
 * @since JDK11
 */

@Getter
@Setter
@ToString(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessDefinitionPageVo extends CamundaDateBasePageVo implements Serializable {
    private static final long serialVersionUID = 1013595912436597889L;

    @Schema(name = "processDefinitionKey", title = "流程定义key")
    private String processDefinitionKey;

    @Schema(name = "name", title = "流程名称")
    private String name;

    @Schema(name = "category", title = "类别")
    private String category;

    @Schema(name = "newVersion", title = "是否只查询最新版本,不选全部")
    private Boolean newVersion;

    @Schema(name = "suspensionState", title = "部署状态,SuspensionState,1-active,2-suspended")
    private Integer suspensionState;
}
