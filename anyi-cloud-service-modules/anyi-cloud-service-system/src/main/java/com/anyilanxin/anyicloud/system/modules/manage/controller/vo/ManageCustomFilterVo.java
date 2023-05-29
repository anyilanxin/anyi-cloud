

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 自定义过滤器添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:14
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
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

    @Schema(name = "specialUrls", title = "特殊url")
    @Valid
    private List<ManageSpecialUrlVo> specialUrls;
}
