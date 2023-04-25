package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由-自定义过滤器表条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:41:23
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class ManageRouteCustomFilterQueryVo implements Serializable {
    private static final long serialVersionUID = 130912333288403656L;

    @Schema(name = "routeCustomFilterId", title = "路由自定义过滤器id")
    private String routeCustomFilterId;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;

    @Schema(name = "routeId", title = "路由id")
    private String routeId;

}
