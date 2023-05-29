

package com.anyilanxin.anyicloud.auth.oauth2.validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 验证码dto
 *
 * @author zxh
 * @date 2020-06-30 15:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
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

    @Schema(name = "codeId", title = "code Id")
    private String codeId;

    @JsonIgnore
    private boolean status;

    @JsonIgnore
    private String msg;
}
