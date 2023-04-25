package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 部门角色-资源表条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgRoleResourceApiQueryVo implements Serializable {
    private static final long serialVersionUID = -25318957070959136L;

    @Schema(name = "roleResourceApiId", title = "资源api角色id")
    private String roleResourceApiId;

    @Schema(name = "orgRoleId", title = "机构角色id")
    private String orgRoleId;

    @Schema(name = "apiId", title = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值")
    private String apiId;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

}
