/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.gateway.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由入参
 *
 * @author zxh
 * @date 2020-09-10 22:43
 * @since 1.0.0
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class GatewayRouteVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5358228979136756020L;

    @Schema(name = "routeCode", title = "路由编码(唯一)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "路由编码(唯一)不能为空")
    private String routeCode;

    @Schema(name = "url", title = "路由url地址,当选择非负载均衡器时必填")
    private String url;

    @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时服务名必填，url不填", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "请确定是否负载均衡器")
    @Builder.Default
    private Integer isLoadBalancer = 0;

    @Schema(name = "loadBalancerType", title = "负载均衡器类型:0-普通,1-ws,2-wss,当选的负载均衡器时必填")
    private String loadBalancerType;

    @Schema(name = "serviceName", title = "服务名,当选择负载均衡器时使用必填")
    private String serviceName;

    @Schema(name = "routeOrder", title = "路由排序,越小越靠前，默认0")
    @Builder.Default
    private Integer routeOrder = 0;

    @Schema(name = "metadata", title = "路由元数据")
    private Map<String, Object> metadata;

    @Schema(name = "routePredicates", title = "路由断言", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNullSize(message = "路由断言不能为空")
    @Valid
    private List<RoutePredicate> routePredicates;

    @Schema(name = "routeFilters", title = "路由过滤器")
    @Valid
    private List<RouteFilter> routeFilters;

    @Getter
    @Setter
    @ToString

    @SuperBuilder(toBuilder = true)

    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class RoutePredicate implements Serializable {
        @Serial
        private static final long serialVersionUID = -67116125178064715L;

        @Schema(name = "predicateType", title = "断言类型", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlankOrNull(message = "断言类型不能为空")
        private String predicateType;

        @Schema(name = "rules", title = "断言规则", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNullSize(message = "断言规则不能为空")
        @Valid
        private Set<Rule> ruleSet;
    }

    @Getter
    @Setter
    @ToString

    @SuperBuilder(toBuilder = true)

    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class RouteFilter implements Serializable {
        @Serial
        private static final long serialVersionUID = 5625992009673170739L;

        @Schema(name = "filterType", title = "过滤器类型", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlankOrNull(message = "过滤器类型不能为空")
        private String filterType;

        @Schema(name = "rules", title = "过滤器规则", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNullSize(message = "过滤器规则不能为空")
        @Valid
        private Set<Rule> ruleSet;
    }

    @Getter
    @Setter
    @ToString

    @SuperBuilder(toBuilder = true)

    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class Rule {
        @Schema(name = "ruleName", title = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlankOrNull(message = "规则名称不能为空")
        private String ruleName;

        @Schema(name = "ruleValue", title = "规则值", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlankOrNull(message = "规则值不能为空")
        private String ruleValue;
    }
}
