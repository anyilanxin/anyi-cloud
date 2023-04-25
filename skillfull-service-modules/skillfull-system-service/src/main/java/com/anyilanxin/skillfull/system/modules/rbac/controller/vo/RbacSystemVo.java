package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 系统添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class RbacSystemVo implements Serializable {
    private static final long serialVersionUID = 169225020104883695L;

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
