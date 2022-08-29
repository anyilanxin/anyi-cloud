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
 * 找回密码
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
public class FindPasswordVo implements Serializable {
    private static final long serialVersionUID = 8153054596114383688L;

    @Schema(name = "newPassword", title = "新密码")
    private String newPassword;

    @Schema(name = "phone", title = "手机号")
    private String phone;

    @Schema(name = "smsCode", title = "短信验证码")
    private String smsCode;
}
