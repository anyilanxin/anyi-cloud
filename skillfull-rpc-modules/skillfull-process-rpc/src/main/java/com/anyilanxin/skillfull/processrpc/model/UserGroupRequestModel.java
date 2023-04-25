package com.anyilanxin.skillfull.processrpc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
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
@EqualsAndHashCode
@Schema
public class UserGroupRequestModel implements Serializable {
    private static final long serialVersionUID = 6364921052776119371L;

    @Schema(name = "groupIds", title = "用户组ids")
    protected Set<String> groupIds;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    private String userId;
}
