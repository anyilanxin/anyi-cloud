// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.stream.router;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * 路由断言
 *
 * @author zxiaozhou
 * @date 2021-12-22 23:35
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RoutePredicateModel implements Serializable {
    private static final long serialVersionUID = 409866466459197284L;

    @Schema(name = "predicateType", title = "断言类型")
    private String predicateType;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "rules", title = "断言规则")
    private Map<String, String> rules;
}
