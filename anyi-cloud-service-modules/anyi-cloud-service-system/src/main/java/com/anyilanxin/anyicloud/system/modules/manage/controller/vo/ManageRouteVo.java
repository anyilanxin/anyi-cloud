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
package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:16
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class ManageRouteVo implements Serializable {
    private static final long serialVersionUID = -75263796196156104L;

    @Schema(name = "routeCode", title = "路由编码(唯一)", required = true)
    @NotBlankOrNull(message = "路由编码不能为空")
    private String routeCode;

    @Schema(name = "serviceId", title = "服务id", required = true)
    @NotBlankOrNull(message = "服务id不能为空")
    private String serviceId;

    @Schema(name = "serviceCode", title = "服务编码,当选择负载均衡器时使用必填")
    private String serviceCode;

    @Schema(name = "routeName", title = "路由名称", required = true)
    @NotBlankOrNull(message = "路由名称不能为空")
    private String routeName;

    @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应", required = true)
    @NotBlankOrNull(message = "是否负载均衡器不能为空")
    private Integer isLoadBalancer;

    @Schema(name = "url", title = "路由url地址,当选择非负载均衡器时必填")
    private String url;

    @Schema(name = "loadBalancerType", title = "负载均衡器类型:0-lb,1-lb:ws,2-lb:wss,来自常量字典:gateway-service:LbType")
    private String loadBalancerType;

    @Schema(name = "metadataJson", title = "路由元数据,数据库json存储,入库前转为字符串")
    private Map<String, String> metadataJson;

    @Schema(name = "routeOrder", title = "路由排序,越小越靠前，默认0")
    private Integer routeOrder;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "routeState", title = "路由状态:0-禁用,1-启用。默认0", required = true)
    @NotBlankOrNull(message = "路由状态:0-禁用,1-启用。默认0不能为空")
    private Integer routeState;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "routeFilters", title = "过滤器")
    private List<ManageRouteFilterVo> routeFilters;

    @Schema(name = "routePredicates", title = "断言", required = true)
    @NotBlankOrNull(message = "断言不能为空")
    @Valid
    private List<ManageRoutePredicateVo> routePredicates;

    @Schema(name = "customFilterIds", title = "自定义过滤器")
    @Valid
    private List<ManageRouteCustomFilterVo> customFilters;
}
