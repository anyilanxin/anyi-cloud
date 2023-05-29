

package com.anyilanxin.anyicloud.process.modules.base.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 模型装填统计
 *
 * @author zxh
 * @date 2020-10-15 22:17:54
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class DesignModelDeploymentStatiDto implements Serializable {
    private static final long serialVersionUID = -11564740755691975L;

    @Schema(name = "noDeployment", title = "未部署")
    private int noDeployment;

    @Schema(name = "deployment", title = "已部署")
    private int deployment;

    @Schema(name = "newVersion", title = "新版本")
    private int newVersion;
}
