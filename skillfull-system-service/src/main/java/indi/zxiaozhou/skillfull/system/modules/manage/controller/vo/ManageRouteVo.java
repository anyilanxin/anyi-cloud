// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 路由添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:16
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
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
    private String metadataJson;

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
