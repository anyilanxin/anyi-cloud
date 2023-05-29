

package com.anyilanxin.anyicloud.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 删除流程模型
 *
 * @author zxh
 * @date 2020-10-20 11:25
 * @since 1.0.0
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
