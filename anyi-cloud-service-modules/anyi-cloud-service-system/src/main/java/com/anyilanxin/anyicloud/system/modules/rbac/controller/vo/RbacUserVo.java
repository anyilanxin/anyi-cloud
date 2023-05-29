

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacUserVo implements Serializable {
    private static final long serialVersionUID = -71393470696705701L;

    @Schema(name = "userName", title = "用户名", required = true)
    @NotBlankOrNull(message = "用户名不能为空")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

    @Schema(name = "realName", title = "真实姓名", required = true)
    @NotBlankOrNull(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "password", title = "密码(新增时必填)")
    private String password;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "additionalInformation", title = "扩展信息,json object")
    private Map<String, Object> additionalInformation;

    @Schema(name = "positionIds", title = "职位,org_id array")
    private Set<String> positionIds;

    @Schema(name = "managerOrgIds", title = "负责机构,org_id array")
    private Set<String> managerOrgIds;

    @Schema(name = "birthday", title = "生日", type = "string", example = "2020-11-12")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "email", title = "邮件")
    private String email;

    @Schema(name = "phone", title = "电话号码")
    private String phone;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1", required = true)
    @NotBlankOrNull(message = "状态能为空")
    @Min(value = 0, message = "状态只能为0、1、2")
    @Max(value = 2, message = "状态只能为0、1、2")
    private Integer userStatus;

    @Schema(name = "workNo", title = "工号，唯一键")
    private String workNo;

    @Schema(name = "telephone", title = "座机号")
    private String telephone;

    @Schema(name = "orgRoleIds", title = "部门角色ids")
    private Set<String> orgRoleIds;

    @Schema(name = "roleIds", title = "用户角色ids")
    private Set<String> roleIds;
}
