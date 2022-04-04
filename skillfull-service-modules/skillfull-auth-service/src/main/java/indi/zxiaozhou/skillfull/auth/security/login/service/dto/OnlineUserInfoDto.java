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
import com.fasterxml.jackson.annotation.JsonIgnore;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * 用户在线信息
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
public class OnlineUserInfoDto implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;

    @Schema(name = "userName", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;

    @Schema(name = "loginFailErrorNum", title = "连续登录错误次数")
    private Integer loginFailErrorNum;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "password", title = "密码")
    @JsonIgnore
    private String password;

    @Schema(name = "salt", title = "密码盐")
    @JsonIgnore
    private String salt;

    @Schema(name = "birthday", title = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

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

    @Schema(name = "currentDepartCode", title = "当前部门code")
    private String currentDepartCode;

    @Schema(name = "currentAreaId", title = "当前区域id(即区域编码)")
    private String currentAreaId;

    @Schema(name = "currentAreaName", title = "当前区域名称名称")
    private String currentAreaName;

    @Schema(name = "currentWholeName", title = "当前区域完整名称")
    private String currentWholeName;

    @Schema(name = "currentPositionId", title = "当前职位id")
    private String currentPositionId;

    @Schema(name = "currentPositionCode", title = "当前职位名称")
    private String currentPositionCode;

    @Schema(name = "currentPositionName", title = "当前职位名称")
    private String currentPositionName;

    @Schema(name = "currentSystemId", title = "当前系统id")
    private String currentSystemId;

    @Schema(name = "currentSystemName", title = "当前系统名称")
    private String currentSystemName;

    @Schema(name = "currentSystemCode", title = "当前系统编码")
    private String currentSystemCode;

    @Schema(name = "orgIds", title = "负责部门")
    private String orgIds;

    @Schema(name = "currentTenantId", title = "当前租户id")
    private String currentTenantId;

    @Schema(name = "onlineInfo", title = "用户登录信息")
    private LoginOnlineInfoModel onlineInfo;

}

