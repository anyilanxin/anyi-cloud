

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 查询某个服务所有实例 vo
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
public class NacosAllInstancesQueryVo implements Serializable {
    private static final long serialVersionUID = 8282161660745482124L;

    @Schema(name = "serviceCode", title = "服务编码")
    @NotBlank(message = "服务编码不能为空")
    private String serviceCode;

    @Schema(name = "groupName", title = "所属组名")
    private String groupName;
}
