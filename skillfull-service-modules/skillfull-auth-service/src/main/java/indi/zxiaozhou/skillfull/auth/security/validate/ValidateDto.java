// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 验证码dto
 *
 * @author zxiaozhou
 * @date 2020-06-30 15:20
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
public class ValidateDto implements Serializable {
    private static final long serialVersionUID = -3819528498303862491L;
    @Schema(name = "codeValue", title = "验证码值")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String codeValue;

    @Schema(name = "codeType", title = "验证码类型")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String codeType;

    @Schema(name = "validTime", title = "验证码有效时间(秒)")
    private Long validTime;

    @Schema(name = "codeId", title = "tokenId")
    private String codeId;

    @JsonIgnore
    private boolean status;

    @JsonIgnore
    private String msg;
}
