package com.anyilanxin.skillfull.system.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由-自定义过滤器表(ManageRouteCustomFilter)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-09 12:02:48
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_manage_route_custom_filter")
public class ManageRouteCustomFilterEntity implements Serializable {
    private static final long serialVersionUID = -20330045402293420L;

    @TableId
    private String routeCustomFilterId;

    /**
     * 自定义过滤器id
     */
    private String customFilterId;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 过滤器类型:来自网关常量FilterCustomPostType,FilterCustomPreType
     */
    private String filterType;
}
