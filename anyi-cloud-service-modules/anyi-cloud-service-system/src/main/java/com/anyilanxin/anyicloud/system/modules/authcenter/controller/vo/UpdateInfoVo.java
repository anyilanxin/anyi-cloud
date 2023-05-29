

package com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 修改用户信息
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
public class UpdateInfoVo implements Serializable {
    private static final long serialVersionUID = -3748041272628412460L;

    @Schema(name = "nickName", title = "用户昵称", required = true)
    @NotBlankOrNull(message = "用户昵称不能为空")
    private String nickName;

    @Schema(name = "shortProfile", title = "个人简介", required = true)
    @NotBlankOrNull(message = "个人简介不能为空")
    private String shortProfile;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "birthday", title = "生日", type = "string", example = "2020-11-12")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "email", title = "邮件")
    private String email;

    @Schema(name = "telephone", title = "座机号")
    private String telephone;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
