

package com.anyilanxin.anyicloud.corecommon.model.stream.router;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由断言
 *
 * @author zxh
 * @date 2021-12-22 23:35
 * @since 1.0.0
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
