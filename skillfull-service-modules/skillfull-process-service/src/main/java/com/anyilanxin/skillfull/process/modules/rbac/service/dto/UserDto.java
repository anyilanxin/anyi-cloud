package com.anyilanxin.skillfull.process.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

import java.io.Serializable;
import java.util.Objects;

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
@EqualsAndHashCode
@Schema
public class UserDto implements Serializable {
    private static final long serialVersionUID = 6364921052776119371L;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "email", title = "电子邮件")
    private String email;

    @Schema(name = "detailInfo", title = "详细信息")
    private UserDetailDto detailInfo;


    public UserDto getUser(User user, UserDetailDto detailInfo) {
        UserDto userModel = null;
        if (Objects.nonNull(user)) {
            UserEntity userEntity = (UserEntity) user;
            userModel = UserDto.builder()
                    .userId(userEntity.getId())
                    .realName(userEntity.getFirstName())
                    .userName(userEntity.getLastName())
                    .email(userEntity.getEmail())
                    .detailInfo(detailInfo)
                    .build();
        }
        return userModel;
    }
}
