

package com.anyilanxin.anyicloud.system.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由-自定义过滤器表(ManageRouteCustomFilter)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-09 12:02:48
 * @since 1.0.0
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
