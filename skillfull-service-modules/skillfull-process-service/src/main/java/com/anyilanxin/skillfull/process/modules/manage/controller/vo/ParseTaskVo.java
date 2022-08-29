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

import java.io.Serializable;

/**
 * 解析bpmn task
 *
 * @author zxiaozhou
 * @date 2021-11-24 14:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ParseTaskVo implements Serializable {
    private static final long serialVersionUID = 4236977857621846382L;

    @Schema(name = "processDefinitionId", title = "流程定义id(与流程定义key必传一个,两个都传认流程定义id),即指定版本")
    private String processDefinitionId;

    @Schema(name = "processDefinitionKey", title = "流程定义key(与流程定义id必传一个,两个都传认流程定义id),运行最新版本")
    private String processDefinitionKey;
}
