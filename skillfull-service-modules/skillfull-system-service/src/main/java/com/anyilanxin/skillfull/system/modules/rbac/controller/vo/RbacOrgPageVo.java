// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 组织表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:39:45
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgPageVo extends BasePageVo {
    private static final long serialVersionUID = -45785621746659393L;

    @Schema(name = "orgName", title = "组织名称")
    private String orgName;

    @Schema(name = "orgCode", title = "组织编码")
    private String orgCode;

    @Schema(name = "orgStatus", title = "组织状态：0-禁用，1-启用，默认0")
    private Integer orgStatus;

    @Schema(name = "socialCode", title = "统一社会信用代码")
    private String socialCode;
}
