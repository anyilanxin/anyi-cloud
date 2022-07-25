// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 实例统计
 *
 * @author zxiaozhou
 * @date 2021-07-30 21:51
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class InstanceStaticDto implements Serializable {
    private static final long serialVersionUID = -796467855703929445L;

    @Schema(name = "total", title = "总实例")
    private long total;

    @Schema(name = "finish", title = "完结实例")
    private long finish;

    @Schema(name = "unFinish", title = "未完结实例")
    private long unFinish;

}
