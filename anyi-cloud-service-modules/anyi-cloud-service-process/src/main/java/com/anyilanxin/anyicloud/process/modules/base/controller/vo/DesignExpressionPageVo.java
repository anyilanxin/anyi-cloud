

package com.anyilanxin.anyicloud.process.modules.base.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程表达式分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-27 00:20:29
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class DesignExpressionPageVo extends BasePageVo {
    private static final long serialVersionUID = -38766260909669200L;

    @Schema(name = "handleType", title = "处理类型:0-人员,1-用户组,2-时间,3-条件,4-其他")
    private Integer handleType;

    @Schema(name = "expressionType", title = "表达式类型:1-表达式,2-委托表达式")
    private Integer expressionType;

    @Schema(name = "expressionState", title = "表达式状态:0-禁用,1-启用")
    private Integer expressionState;

    @Schema(name = "keyword", title = "表达式名称或el值搜索")
    private String keyword;
}
