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
import java.time.LocalDate;

/**
 * 图片验证码vo
 *
 * @author zxiaozhou
 * @date 2020-06-30 15:49
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SmsCodeVo implements Serializable {
    private static final long serialVersionUID = 2012658056429032034L;

    @Schema(name = "codeId", title = "验证码id")
    @NotBlank(message = "图片验证码标识不能为空")
    private String codeId;

    @Schema(name = "codeValue", title = "验证码value")
    @NotBlank(message = "图片验证码不能为空")
    private String codeValue;

    @Schema(name = "phone", title = "手机号码value", example = "oiuoiuououioi")
    @NotBlank(message = "手机号码不能为空")
    private String phone;


    @Schema(name = "phone", title = "手机号码value", example = "2020-11-10")
    @NotBlank(message = "手机号码不能为空")
    private LocalDate localDate;
}
