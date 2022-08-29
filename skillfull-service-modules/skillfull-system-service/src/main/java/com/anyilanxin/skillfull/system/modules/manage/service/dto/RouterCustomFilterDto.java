// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import com.anyilanxin.skillfull.corecommon.model.stream.router.RouteFilterModel;
import com.anyilanxin.skillfull.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 自定义过滤器查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:14
 * @since JDK1.8
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
