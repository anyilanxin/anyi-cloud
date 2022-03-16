// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * 用户表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:16
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
public class RbacUserVo implements Serializable {
    private static final long serialVersionUID = -95485480358197097L;

    @Schema(name = "userName", title = "用户名", required = true)
    @NotBlankOrNull(message = "用户名不能为空")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "realName", title = "真实姓名", required = true)
    @NotBlankOrNull(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "password", title = "密码(添加时必填)")
    private String password;

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

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1", required = true)
    @NotBlankOrNull(message = "状态不能为空")
    @Min(value = 0, message = "状态只能为0、1、2")
    @Max(value = 2, message = "状态只能为0、1、2")
    private Integer userStatus;

    @Schema(name = "workNo", title = "工号，唯一键")
    private String workNo;

    @Schema(name = "telephone", title = "座机号")
    private String telephone;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "orgId", title = "所属机构")
    private String orgId;

    @Schema(name = "positionIds", title = "所属职位")
    private List<String> positionIds;

    @Schema(name = "roleIds", title = "角色信息")
    private List<String> roleIds;
}