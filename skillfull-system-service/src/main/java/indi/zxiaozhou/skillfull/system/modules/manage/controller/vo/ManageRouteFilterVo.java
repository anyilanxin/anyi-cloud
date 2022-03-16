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
 * 路由过滤器添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 10:37:41
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
public class ManageRouteFilterVo implements Serializable {
    private static final long serialVersionUID = 645976330522385000L;

    @Schema(name = "filterType", title = "过滤器类型", required = true)
    @NotBlankOrNull(message = "过滤器类型不能为空")
    private String filterType;

    @Schema(name = "filterTypeName", title = "过滤器类型名称", required = true)
    @NotBlankOrNull(message = "过滤器类型名称不能为空")
    private String filterTypeName;

    @Schema(name = "filterName", title = "过滤器名称", required = true)
    @NotBlankOrNull(message = "过滤器名称不能为空")
    private String filterName;

    @Schema(name = "rules", title = "过滤器规则:{key:value}")
    private String rules;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
