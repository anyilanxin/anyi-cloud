package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由-自定义过滤器表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:17
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageRouteCustomFilterPageVo extends BasePageVo {
    private static final long serialVersionUID = -78611418971003696L;

    @Schema(name = "routeCustomFilterId", title = "路由自定义过滤器id")
    private String routeCustomFilterId;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;

    @Schema(name = "routeId", title = "路由id")
    private String routeId;

}
