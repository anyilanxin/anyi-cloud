

package com.anyilanxin.anyicloud.gatewayrpc.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由查询响应
 *
 * @author zxh
 * @date 2020-09-10 22:48
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RouteResponseModel implements Serializable {
    private static final long serialVersionUID = 8405189730553034504L;

    @Schema(name = "routeCode", title = "路由编码(唯一)")
    private String routeCode;

    @Schema(name = "url", title = "路由url地址,当选择非负载均衡器时必填")
    private String url;

    @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时服务名必填，url不填")
    private Integer isLoadBalancer;

    @Schema(name = "loadBalancerType", title = "负载均衡器类型:0-普通,1-ws,2-wss,当选的负载均衡器时必填")
    private String loadBalancerType;

    @Schema(name = "serviceName", title = "服务名,当选择负载均衡器时使用必填")
    private String serviceName;

    @Schema(name = "routeOrder", title = "路由排序,越小越靠前，默认0")
    private Integer routeOrder;

    @Schema(name = "metadata", title = "路由元数据")
    private JSONObject metadata;

    @Schema(name = "routePredicates", title = "路由断言")
    private List<RoutePredicate> routePredicates;

    @Schema(name = "routeFilters", title = "路由过滤器")
    private List<RouteFilter> routeFilters;

    @Getter
    @Setter
    @ToString
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class RoutePredicate implements Serializable {
        private static final long serialVersionUID = -67116125178064715L;

        @Schema(name = "predicateType", title = "断言类型")
        private String predicateType;

        @Schema(name = "rules", title = "断言规则")
        private Set<Rule> rules;
    }

    @Getter
    @Setter
    @ToString
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class RouteFilter implements Serializable {
        private static final long serialVersionUID = 5625992009673170739L;

        @Schema(name = "filterType", title = "过滤器类型")
        private String filterType;

        @Schema(name = "rules", title = "过滤器规则")
        private Set<Rule> rules;
    }

    @Getter
    @Setter
    @ToString
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class Rule implements Serializable {
        private static final long serialVersionUID = 4539311862270008460L;

        @Schema(name = "ruleName", title = "规则名称")
        private String ruleName;

        @Schema(name = "ruleValue", title = "规则值")
        private String ruleValue;
    }
}
