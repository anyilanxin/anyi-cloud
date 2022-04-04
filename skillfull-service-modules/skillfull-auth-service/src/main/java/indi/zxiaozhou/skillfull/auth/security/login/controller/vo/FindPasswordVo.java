// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 图片验证码登录vo
 *
 * @author zxiaozhou
 * @date 2020-07-08 19:05
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class FindPasswordVo implements Serializable {
    private static final long serialVersionUID = 7229979897479559294L;

    @Schema(name = "password", title = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(name = "phone", title = "电话号码", required = true)
    @NotBlank(message = "电话号码不能为空")
    private String phone;

    @Schema(name = "code", title = "短信验证码", required = true)
    @NotBlank(message = "短信验证码不能为空")
    private String code;
}
