// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.authcenter.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 修改密码
 *
 * @author zxiaozhou
 * @date 2022-05-02 09:40
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class UpdatePasswordVo implements Serializable {
    private static final long serialVersionUID = -2436630973760520029L;

    @Schema(name = "oldPassword", title = "原始密码")
    private String oldPassword;

    @Schema(name = "newPassword", title = "新密码")
    private String newPassword;
}
