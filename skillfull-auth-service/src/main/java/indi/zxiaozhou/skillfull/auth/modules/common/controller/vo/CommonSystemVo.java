package indi.zxiaozhou.skillfull.auth.modules.common.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 系统添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-07-28 10:13:05
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
public class CommonSystemVo implements Serializable {
    private static final long serialVersionUID = -19013331833294473L;

    @Schema(name = "systemName", title = "系统名称", required = true)
    @NotBlankOrNull(message = "系统名称不能为空")
    private String systemName;

    @Schema(name = "systemCode", title = "系统编码", required = true)
    @NotBlankOrNull(message = "系统编码不能为空")
    private String systemCode;

    @Schema(name = "systemDescribe", title = "系统描述")
    private String systemDescribe;

    @Schema(name = "icon", title = "系统图标")
    private String icon;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
