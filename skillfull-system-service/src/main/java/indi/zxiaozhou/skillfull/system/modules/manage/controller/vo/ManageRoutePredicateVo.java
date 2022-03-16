// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由断言添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 10:37:42
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ManageRoutePredicateVo implements Serializable {
    private static final long serialVersionUID = 927432054061925798L;

    @Schema(name = "predicateType", title = "断言类型", required = true)
    @NotBlankOrNull(message = "断言类型不能为空")
    private String predicateType;

    @Schema(name = "predicateName", title = "断言名称", required = true)
    @NotBlankOrNull(message = "断言名称不能为空")
    private String predicateName;

    @Schema(name = "predicateTypeName", title = "断言类型名称", required = true)
    @NotBlankOrNull(message = "断言类型名称不能为空")
    private String predicateTypeName;

    @Schema(name = "rules", title = "断言规则:[{ruleName:规则名称,ruleValue:规则值}]")
    private String rules;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
