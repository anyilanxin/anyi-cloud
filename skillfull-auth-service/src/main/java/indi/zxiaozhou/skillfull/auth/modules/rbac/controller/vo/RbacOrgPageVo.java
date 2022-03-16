// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo;

import indi.zxiaozhou.skillfull.coredatabase.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 组织表分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-01-19 12:59:46
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacOrgPageVo extends BasePageVo {
    private static final long serialVersionUID = -28467079406639001L;

    @Schema(name = "orgName", title = "组织名称")
    private String orgName;

    @Schema(name = "orgCode", title = "组织编码")
    private String orgCode;

    @Schema(name = "orgStatus", title = "组织状态：0-禁用，1-启用，默认0")
    private Integer orgStatus;
}