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

import java.util.Set;

/**
 * @author zxiaozhou
 * @date 2021-11-24 15:11
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@Schema
public class FlowNodeInfoDto {

    private String id;

    private String name;

    private String typeName;

    private Set<String> incomingSourceIds;

    private Set<String> incomingTargetIds;

    private Set<String> outgoingSourceIds;

    private Set<String> outgoingTargetIds;
}
