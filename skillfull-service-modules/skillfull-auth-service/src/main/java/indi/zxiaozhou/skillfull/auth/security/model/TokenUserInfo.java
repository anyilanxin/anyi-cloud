// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * 登录响应token信息
 *
 * @author zxiaozhou
 * @date 2020-06-29 14:33
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class TokenUserInfo implements Serializable {
    private static final long serialVersionUID = 783443996779878395L;

    @Schema(name = "token", title = "token")
    private String token;

    @Schema(name = "userInfo", title = "用户信息")
    private UserInfo userInfo;

    @Schema(name = "roles", title = "角色信息")
    private Set<String> roles;


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class UserInfo implements Serializable {
        private static final long serialVersionUID = -7216984493036628645L;

        @Schema(name = "userName", title = "用户id")
        private String userId;

        @Schema(name = "userName", title = "用户名")
        private String userName;

        @Schema(name = "realName", title = "真实姓名")
        private String realName;

        @Schema(name = "avatar", title = "头像")
        private String avatar;

        @Schema(name = "birthday", title = "生日")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
        private Date birthday;

        @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
        private Integer sex;

        @Schema(name = "email", title = "邮件")
        private String email;

        @Schema(name = "phone", title = "电话号码")
        private String phone;

        @Schema(name = "currentDepartId", title = "当前部门id")
        private String currentDepartId;

        @Schema(name = "currentDepartName", title = "当前部门名称")
        private String currentDepartName;

        @Schema(name = "orgIds", title = "负责部门")
        private String orgIds;
    }

}
