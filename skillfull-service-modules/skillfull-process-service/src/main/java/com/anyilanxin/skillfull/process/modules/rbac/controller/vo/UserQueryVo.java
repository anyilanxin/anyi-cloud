// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

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
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class UserQueryVo implements Serializable {
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
