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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-资源表条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacRoleResourceApiQueryVo implements Serializable {
    private static final long serialVersionUID = 312780282135991181L;

    @Schema(name = "roleResourceApiId", title = "资源api角色id")
    private String roleResourceApiId;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "apiId", title = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值")
    private String apiId;

}
