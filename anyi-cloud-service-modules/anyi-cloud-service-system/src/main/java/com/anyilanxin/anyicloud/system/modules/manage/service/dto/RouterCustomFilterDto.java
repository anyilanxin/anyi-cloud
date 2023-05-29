

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import com.anyilanxin.anyicloud.corecommon.model.stream.router.RouteFilterModel;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 自定义过滤器查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:14
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RouterCustomFilterDto implements Serializable {
    private static final long serialVersionUID = -90187804208911654L;

    @Schema(name = "filterModels", title = "过滤器")
    private List<RouteFilterModel> filterModels;

    @Schema(name = "specialUrls", title = "特殊url:map<过滤器filterType(FilterCustomPreType、FilterCustomPostType),特殊url>")
    private Map<String, RouteMetaSpecialUrlModel> specialUrls;
}
