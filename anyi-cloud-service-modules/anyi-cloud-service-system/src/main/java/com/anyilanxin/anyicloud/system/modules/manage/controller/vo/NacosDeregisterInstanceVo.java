

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 删除服务实例 vo
 *
 * @author zxh zxiaozhou
 * @date 2020-10-11 16:38
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class NacosDeregisterInstanceVo implements Serializable {
    private static final long serialVersionUID = 8282161660745482124L;

    @Schema(name = "serviceCode", title = "服务编码", required = true)
    @NotBlank(message = "服务编码不能为空")
    private String serviceCode;

    @Schema(name = "ip", title = "ip", required = true)
    @NotBlank(message = "ip不能为空")
    private String ip;

    @Schema(name = "port", title = "端口", required = true)
    @NotNull(message = "端口不能为空")
    private Integer port;
}
