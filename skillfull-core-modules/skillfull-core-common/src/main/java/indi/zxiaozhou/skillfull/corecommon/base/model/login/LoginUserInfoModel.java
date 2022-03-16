// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.ActionBackendModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.AgentModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.DataModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.RoleModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户信息与权限信息
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
public class LoginUserInfoModel implements Serializable {
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

    @Schema(name = "currentOrgId", title = "当前机构id")
    private String currentOrgId;

    @Schema(name = "currentOrgName", title = "当前机构名称")
    private String currentOrgName;

    @Schema(name = "currentOrgCode", title = "当前机构code")
    private String currentOrgCode;

    @Schema(name = "currentTenantId", title = "当前租户id")
    private String currentTenantId;

    @Schema(name = "actionInfo", title = "用户后台所有按钮权限信息")
    private ActionBackendModel actionInfo;

    @Schema(name = "roleInfo", title = "当前角色信息")
    private Set<RoleModel> roleInfo;

    @Schema(name = "agentInfo", title = "代理人信息")
    private Set<AgentModel> agentInfo;

    @Schema(name = "dataInfo", title = "数据权限信息")
    private Set<DataModel> dataInfo;

    @Schema(name = "otherInfo", title = "用户其他信息")
    private Object otherInfo;
}

