// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 创建流程实例
 *
 * @author zxiaozhou
 * @date 2020-10-19 16:58
 * @since JDK11
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
