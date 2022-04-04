// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * 用户表查询Response
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:16
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
public class RbacUserDto implements Serializable {
    private static final long serialVersionUID = 854437047595834340L;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

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

    @Schema(name = "orgIds", title = "负责部门")
    private String orgIds;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;

    @Schema(name = "workNo", title = "工号，唯一键")
    private String workNo;

    @Schema(name = "telephone", title = "座机号")
    private String telephone;

    @Schema(name = "currentLoginTime", title = "最近登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentLoginTime;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "orgInfo", title = "所属机构")
    private OrgInfoDto orgInfo;

    @Schema(name = "orgId", title = "所属机构id")
    private String orgId;

    @Schema(name = "positionInfos", title = "职位")
    private List<PositionInfoDto> positionInfos;

    @Schema(name = "positionIds", title = "所属职位id")
    private List<String> positionIds;

    @Schema(name = "roleInfos", title = "职位")
    private List<RbacCorrelateRoleDto> roleInfos;

    @Schema(name = "roleIds", title = "角色id")
    private List<String> roleIds;

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class OrgInfoDto {
        @Schema(name = "orgId", title = "机构id")
        private String orgId;

        @Schema(name = "orgCode", title = "组织编码")
        private String orgCode;

        @Schema(name = "orgSysCode", title = "组织编码(系统)")
        private String orgSysCode;

        @Schema(name = "orgName", title = "组织名称")
        private String orgName;

        @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
        private Integer autoBind;

        @Schema(name = "orgType", title = "组织机构类型：1-公司,2-部门")
        private Integer orgType;
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class PositionInfoDto {
        @Schema(name = "positionId", title = "职位id")
        private String positionId;

        @Schema(name = "positionCode", title = "职位编码")
        private String positionCode;

        @Schema(name = "positionName", title = "职位名称")
        private String positionName;

        @Schema(name = "positionRank", title = "职级")
        private Integer positionRank;

        @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
        private Integer autoBind;
    }
}