// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.modules.manage.service.dto;

import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.RouteMetaSpecialUrlModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由查询响应
 *
 * @author zxiaozhou
 * @date 2020-09-10 22:48
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
public class GatewayRouteDto implements Serializable {
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

    @Schema(name = "specialUrls", title = "过滤器特殊url")
    private Map<String, RouteMetaSpecialUrlModel> specialUrls;

    @Schema(name = "routeActions", title = "路由后端按钮权限指令,path:ActionModel")
    private Map<String, Set<ActionModel>> routeActions;

    @Schema(name = "routePredicates", title = "路由断言")
    private List<RoutePredicate> routePredicates;

    @Schema(name = "routeFilters", title = "路由过滤器")
    private List<RouteFilter> routeFilters;

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
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
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
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
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class Rule implements Serializable {

        private static final long serialVersionUID = -7095243410635985896L;
        @Schema(name = "ruleName", title = "规则名称")
        private String ruleName;

        @Schema(name = "ruleValue", title = "规则值")
        private String ruleValue;
    }
}
