

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 删除服务 vo
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
public class NacosDeleteInstanceVo implements Serializable {
    private static final long serialVersionUID = 8282161660745482124L;

    @Schema(name = "serviceName", title = "服务名称")
    @NotBlank(message = "服务名称不能为空")
    private String serviceName;

    @Schema(name = "groupName", title = "所属组名")
    private String groupName;
}
