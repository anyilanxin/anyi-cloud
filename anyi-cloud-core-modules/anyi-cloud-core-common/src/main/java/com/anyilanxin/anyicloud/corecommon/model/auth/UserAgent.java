

package com.anyilanxin.anyicloud.corecommon.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 代理人
 *
 * @author zxh
 * @date 2021-06-04 00:18
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class UserAgent implements Serializable {
    private static final long serialVersionUID = -2447072397405849742L;

    @Schema(name = "userName", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAgent)) {
            return false;
        }
        UserAgent agentInfo = (UserAgent) o;
        return Objects.equals(getUserId(), agentInfo.getUserId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
