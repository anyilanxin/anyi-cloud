

package com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 修改密码
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
public class UpdatePasswordVo implements Serializable {
    private static final long serialVersionUID = -2436630973760520029L;

    @Schema(name = "oldPassword", title = "原始密码")
    private String oldPassword;

    @Schema(name = "newPassword", title = "新密码")
    private String newPassword;
}
