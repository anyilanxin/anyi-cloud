// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 流程类别条件查询Request
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class ProcessCategoryQueryVo implements Serializable {
    private static final long serialVersionUID = 451770156051417372L;

    @Schema(name = "categoryCode", title = "类别编码(唯一)")
    private String categoryCode;

    @Schema(name = "categoryName", title = "类别名称")
    private String categoryName;

    @Schema(name = "categoryState", title = "类别状态:0-禁用,1-启用。默认0")
    private Integer categoryState;
}
