package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.process.core.base.controller.vo.CamundaNoDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class UserQueryPageVoCamunda extends CamundaNoDateBasePageVo {
    private static final long serialVersionUID = 6364921052776119371L;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "email", title = "电子邮件")
    private String email;

    @Schema(name = "groupId", title = "用户组id")
    private String groupId;

    @Schema(name = "tenantId", title = "租户id")
    private String tenantId;
}
