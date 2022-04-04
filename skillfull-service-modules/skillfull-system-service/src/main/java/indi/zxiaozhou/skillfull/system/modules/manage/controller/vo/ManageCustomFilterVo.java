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
 * 自定义过滤器添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:14
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
public class ManageCustomFilterVo implements Serializable {
    private static final long serialVersionUID = -68994356874750693L;

    @Schema(name = "serviceId", title = "服务id", required = true)
    @NotBlankOrNull(message = "服务id不能为空")
    private String serviceId;

    @Schema(name = "filterName", title = "过滤器名称", required = true)
    @NotBlankOrNull(message = "过滤器名称不能为空")
    private String filterName;

    @Schema(name = "filterTypeName", title = "过滤器类型名称", required = true)
    @NotBlankOrNull(message = "过滤器类型名称不能为空")
    private String filterTypeName;

    @Schema(name = "filterType", title = "过滤器类型", required = true)
    @NotBlankOrNull(message = "过滤器类型不能为空")
    private String filterType;

    @Schema(name = "filterStatus", title = "过滤器状态:0-禁用,1-启用，默认0", required = true)
    @NotBlankOrNull(message = "过滤器状态不能为空")
    private Integer filterStatus;

    @Schema(name = "haveSpecial", title = "是否有特殊url:0-没有,1-有。默认0")
    @Builder.Default
    private Integer haveSpecial = 0;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)")
    private Integer specialUrlType;

    @Schema(name = "specialUrls", title = "特殊url")
    @Valid
    private List<ManageSpecialUrlVo> specialUrls;

}
