// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由查询Response
 *
 * @author zxiaozhou
 * @date 2020-09-12 16:04:47
 * @since JDK1.8
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
public class ValidServiceInfoDto implements Serializable {
    private static final long serialVersionUID = 550568918804547444L;

    @Schema(name = "label", title = "服务名称")
    private String label;

    @Schema(name = "value", title = "服务id")
    private String value;

    @Schema(name = "disabled", title = "是否启用")
    private boolean disabled;
}
