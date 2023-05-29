

package com.anyilanxin.anyicloud.corecommon.model.system;

import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息与权限信息
 *
 * @author zxh
 * @date 2020-07-01 03:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Schema
@NoArgsConstructor
public class UserAndResourceAuthModel extends UserInfo implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;

    @Schema(name = "password", title = "密码")
    private String password;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;

    @Schema(name = "salt", title = "密码盐")
    private String salt;

    @Schema(name = "actions", title = "按钮权限,key为资源id,值为权限指令")
    Map<String, Set<String>> actions;
}
