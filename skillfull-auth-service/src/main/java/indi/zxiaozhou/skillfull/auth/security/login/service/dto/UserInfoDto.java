// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * 用户所有信息
 *
 * @author zxiaozhou
 * @date 2020-07-01 03:21
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class UserInfoDto implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;

    @Schema(name = "userName", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "birthday", title = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

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

    @Schema(name = "roleCodes", title = "当前用户所有角色编码")
    private Set<String> roleCodes;
}

