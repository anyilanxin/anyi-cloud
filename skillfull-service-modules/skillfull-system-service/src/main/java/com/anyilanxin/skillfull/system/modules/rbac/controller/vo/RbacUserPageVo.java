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
 * 用户表分页查询Request
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
public class RbacUserPageVo extends BasePageVo {
    private static final long serialVersionUID = -64411430629112483L;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

    @Schema(name = "ignoreOrgId", title = "忽略机构用户")
    private String ignoreOrgId;

    @Schema(name = "keyword", title = "用户名或手机号或真实姓名查询")
    private String keyword;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;
}
