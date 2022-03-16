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

import indi.zxiaozhou.skillfull.coredatabase.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典表分页查询Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:18
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CommonDictPageVo extends BasePageVo {
    private static final long serialVersionUID = 155865653502537066L;

    @Schema(name = "keyword", title = "字典名称或编码")
    private String keyword;

    @Schema(name = "dictStatus", title = "字典状态：1启用，0禁用，默认0")
    private Integer dictStatus;

    @Schema(name = "dictType", title = "字典类型：0-字符串,1-数字,2-布尔。默认0")
    private Integer dictType;
}