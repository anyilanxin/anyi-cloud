// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.base.model.system;

import com.anyilanxin.skillfull.corecommon.auth.model.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 用户信息与权限信息
 *
 * @author zxiaozhou
 * @date 2020-07-01 03:21
 * @since JDK11
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

