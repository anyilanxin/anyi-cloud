// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 实例分页信息
 *
 * @author zxiaozhou
 * @date 2021-07-30 21:51
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class InstancePageDto implements Serializable {
    private static final long serialVersionUID = -796467855703929445L;

    @Schema(name = "versionTag", title = "部署版本标签")
    protected String versionTag;

    @Schema(name = "version", title = "部署版本")
    protected Integer version;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "diagramData", title = "bpmn blob文件(base64编码)")
    private String diagramData;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;

    @Schema(name = "suspended", title = "是否挂起")
    private Boolean suspended;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

}
