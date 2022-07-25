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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 流程信息
 *
 * @author zxiaozhou
 * @date 2021-11-24 15:42
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@Schema
public class ProcessInfoDto11 implements Serializable {
    private static final long serialVersionUID = -3101398365454871777L;

    @Schema(name = "processDefinitionKey")
    private String processDefinitionKey;

    @Schema(name = "processName")
    private String processName;

    @Schema(name = "total")
    private FlowNodeInfoDto startNodeInfo;

    @Schema(name = "flowNodeInfoList")
    private List<FlowNodeInfoDto> flowNodeInfoList;
}
