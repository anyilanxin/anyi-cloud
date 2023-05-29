

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由过滤器添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 10:37:41
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class ManageRouteFilterVo implements Serializable {
    private static final long serialVersionUID = 645976330522385000L;

    @Schema(name = "filterType", title = "过滤器类型", required = true)
    @NotBlankOrNull(message = "过滤器类型不能为空")
    private String filterType;

    @Schema(name = "filterTypeName", title = "过滤器类型名称", required = true)
    @NotBlankOrNull(message = "过滤器类型名称不能为空")
    private String filterTypeName;

    @Schema(name = "filterName", title = "过滤器名称", required = true)
    @NotBlankOrNull(message = "过滤器名称不能为空")
    private String filterName;

    @Schema(name = "rules", title = "过滤器规则:{key:value}")
    private Map<String, String> rules;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
