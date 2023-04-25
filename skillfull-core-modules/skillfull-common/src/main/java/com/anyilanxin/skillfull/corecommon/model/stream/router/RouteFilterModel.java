package com.anyilanxin.skillfull.corecommon.model.stream.router;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * 路由过滤器
 *
 * @author zxiaozhou
 * @date 2021-12-22 23:36
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RouteFilterModel implements Serializable {
    private static final long serialVersionUID = 849919338654780669L;

    @Schema(name = "filterType", title = "过滤器id")
    private String filterId;

    @Schema(name = "filterType", title = "过滤器类型")
    private String filterType;

    @Schema(name = "rules", title = "过滤器规则")
    private Map<String, String> rules;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
