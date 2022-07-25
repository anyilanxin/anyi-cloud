// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 删除流程模型
 *
 * @author zxiaozhou
 * @date 2020-10-20 11:25
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DeleteDesignModelVo implements Serializable {
    private static final long serialVersionUID = -3016812291917256003L;

    @Schema(name = "modelId", title = "模型id不能为空", required = true)
    @NotBlank(message = "模型id不能为空")
    private String modelId;

    @Schema(name = "cascade", title = "是否级联，默认false")
    @Builder.Default
    private Boolean cascade = false;

    @Schema(name = "skipCustomListeners", title = "是否跳过自定义监听器，默认false")
    @Builder.Default
    private Boolean skipCustomListeners = false;

    @Schema(name = "skipIoMappings", title = "是否跳过io映射，默认false")
    @Builder.Default
    private Boolean skipIoMappings = false;
}
