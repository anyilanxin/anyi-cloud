

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacEnalbeUserPageVo extends BasePageVo {
    private static final long serialVersionUID = -64411430629112483L;

    @Schema(name = "orgId", title = "机构id", required = true)
    @NotBlank(message = "机构id不能为空")
    private String orgId;

    @Schema(name = "keyword", title = "用户名或手机号或真实姓名查询")
    private String keyword;
}
