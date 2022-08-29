// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程类别分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ProcessCategoryPageVo extends BasePageVo {
    private static final long serialVersionUID = 185792104685254828L;

    @Schema(name = "categoryName", title = "类别名称")
    private String categoryName;

    @Schema(name = "categoryState", title = "类别状态:0-禁用,1-启用。默认0")
    private Integer categoryState;
}
