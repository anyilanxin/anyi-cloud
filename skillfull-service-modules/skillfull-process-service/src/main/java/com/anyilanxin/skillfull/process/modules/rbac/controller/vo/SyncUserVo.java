package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 用户信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder

@NoArgsConstructor
@Schema
public class SyncUserVo implements Serializable {
    private static final long serialVersionUID = 6364921052776119371L;
    @Schema(name = "groupIds", title = "用户组ids")
    protected Set<String> groupIds;

    @Schema(name = "tenantIds", title = "租户ids")
    protected Set<String> tenantIds;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @Schema(name = "userName", title = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @Schema(name = "realName", title = "真实姓名", required = true)
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "email", title = "电子邮件")
    private String email;

    @Schema(name = "detailInfo", title = "详细信息")
    private UserDetailVo detailInfo;

    public User getCamundaUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setEmail(email);
        userEntity.setFirstName(realName);
        userEntity.setLastName(userName);
        return userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SyncUserVo)) {
            return false;
        }
        SyncUserVo that = (SyncUserVo) o;
        return Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
