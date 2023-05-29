

package com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 修改手机号
 *
 * @author zxh
 * @date 2022-05-02 09:40
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class UpdatePhoneVo implements Serializable {
    private static final long serialVersionUID = 3879852206170456367L;

    @Schema(name = "phone", title = "手机号")
    private String phone;

    @Schema(name = "smsCode", title = "短信验证码")
    private String smsCode;
}
