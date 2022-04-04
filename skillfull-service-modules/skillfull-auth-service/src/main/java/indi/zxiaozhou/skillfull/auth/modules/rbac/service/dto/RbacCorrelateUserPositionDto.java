// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 组织表查询Response
 *
 * @author zxiaozhou
 * @date 2021-01-19 12:59:36
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacCorrelateUserPositionDto implements Serializable {
    private static final long serialVersionUID = -23698600939298122L;

    @Schema(name = "positionId", title = "职位id")
    private String positionId;

    @Schema(name = "positionCode", title = "职位编码")
    private String positionCode;

    @Schema(name = "positionName", title = "职位名称")
    private String positionName;

    @Schema(name = "positionRank", title = "职级")
    private Integer positionRank;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
    private Integer autoBind;
}