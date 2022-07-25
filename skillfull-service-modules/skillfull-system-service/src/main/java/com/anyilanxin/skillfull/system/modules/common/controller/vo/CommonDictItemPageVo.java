// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典配置项表分页查询Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:25
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class CommonDictItemPageVo extends BasePageVo {
    private static final long serialVersionUID = 853710730536860187L;

    @Schema(name = "keyword", title = "字典项名称、值，字典编码、名称")
    private String keyword;

    @Schema(name = "itemStatus", title = "字典项状态：1启用，0不启用，默认0")
    private Integer itemStatus;

    @Schema(name = "dictId", title = "字典id")
    private String dictId;
}