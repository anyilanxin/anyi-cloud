package com.anyilanxin.skillfull.auth.oauth2.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 验证码验证model
 *
 * @author zxiaozhou
 * @date 2020-06-29 11:07
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CheckModel {
    /**
     * 验证码id
     */
    private String codeId;

    /**
     * 验证码value
     */
    private String codeValue;
}
