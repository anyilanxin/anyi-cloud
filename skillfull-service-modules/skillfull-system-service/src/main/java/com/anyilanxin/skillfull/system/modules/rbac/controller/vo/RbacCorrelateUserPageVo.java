// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户-关联关系表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacCorrelateUserPageVo extends BasePageVo {
    private static final long serialVersionUID = 439696397750435877L;

    @Schema(name = "correlateUserId", title = "用户关联关系id")
    private String correlateUserId;

    @Schema(name = "correlateId", title = "关联id")
    private String correlateId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构,2-职位,3-用户组")
    private Integer correlateType;

    @Schema(name = "userId", title = "用户id")
    private String userId;

}
