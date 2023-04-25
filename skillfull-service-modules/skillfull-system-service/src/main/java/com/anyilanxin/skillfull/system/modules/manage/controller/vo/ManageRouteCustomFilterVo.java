package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由-自定义过滤器表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:17
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class ManageRouteCustomFilterVo implements Serializable {
    private static final long serialVersionUID = 954657272680952491L;

    @Schema(name = "customFilterId", title = "自定义过滤器id", required = true)
    @NotBlankOrNull(message = "自定义过滤器id不能为空")
    private String customFilterId;

    @Schema(name = "filterType", title = "过滤器类型:来自网关常量FilterCustomPostType,FilterCustomPreType", required = true)
    @NotBlankOrNull(message = "过滤器类型不能为空")
    private String filterType;

}
