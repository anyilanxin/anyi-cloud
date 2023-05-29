

package com.anyilanxin.anyicloud.corecommon.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 创建流程实例
 *
 * @author zxh
 * @date 2020-10-19 16:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SubmitFormModel implements Serializable {
    private static final long serialVersionUID = 3926458899033457164L;

    @Schema(name = "value", title = "表单值(时间类型时必须有时间格式，同时该值为已经格式化后的字符串)", required = true)
    @NotBlank(message = "表单值不能为空")
    private Object value;

    @Schema(name = "time", title = "是否时间类型:true-是,false-不是")
    private boolean time;

    @Schema(name = "timeFormat", title = "时间格式")
    private String timeFormat;
}
