// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 分类字典表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-01-07 23:40:01
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonCategoryVo implements Serializable {
    private static final long serialVersionUID = 348923460711945337L;

    @Schema(name = "parentId", title = "父级id")
    private String parentId;

    @Schema(name = "categoryName", title = "分类名称", required = true)
    @NotBlankOrNull(message = "分类名称不能为空")
    private String categoryName;

    @Schema(name = "categoryCommonCode", title = "分类统一编码", required = true)
    @NotBlankOrNull(message = "分类统一编码不能为空")
    private String categoryCommonCode;

    @Schema(name = "categoryCode", title = "分类编码", required = true)
    @NotBlankOrNull(message = "分类编码不能为空")
    private String categoryCode;

    @Schema(name = "isParent", title = "是否父节:0-不是，1-是，默认0", required = true)
    @NotBlankOrNull(message = "是否父节:0-不是，1-是，默认0不能为空")
    private Integer isParent;

    @Schema(name = "remark", title = "备注")
    private String remark;
}