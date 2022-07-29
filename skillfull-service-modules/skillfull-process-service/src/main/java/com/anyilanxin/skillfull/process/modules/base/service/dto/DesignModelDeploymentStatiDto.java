// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 模型装填统计
 *
 * @author zxiaozhou
 * @date 2020-10-15 22:17:54
 * @since JDK11
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
