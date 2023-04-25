package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * 用户表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
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
public class RbacEnalbeUserPageVo extends BasePageVo {
    private static final long serialVersionUID = -64411430629112483L;

    @Schema(name = "orgId", title = "机构id", required = true)
    @NotBlank(message = "机构id不能为空")
    private String orgId;

    @Schema(name = "keyword", title = "用户名或手机号或真实姓名查询")
    private String keyword;
}
