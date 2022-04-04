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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务实例上下线 vo
 *
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 16:38
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class NacosUpdateInstanceVo implements Serializable {
    private static final long serialVersionUID = 8282161660745482124L;

    @Schema(name = "serviceCode", title = "服务编码")
    @NotBlank(message = "服务编码不能为空")
    private String serviceCode;

    @Schema(name = "instanceId", title = "服务实例id")
    @NotBlank(message = "服务实例id不能为空")
    private String instanceId;

    @Schema(name = "type", title = "操作类型:0-下线,1-上下")
    @NotNull(message = "操作类型不能为空")
    @Min(value = 0, message = "操作类型只能为0、1")
    @Max(value = 1, message = "操作类型只能为0、1")
    private Integer type;
}
