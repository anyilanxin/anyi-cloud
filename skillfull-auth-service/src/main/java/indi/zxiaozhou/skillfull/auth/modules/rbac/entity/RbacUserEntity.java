// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户表(RbacUser)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:27:27
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth_rbac_user")
public class RbacUserEntity extends BaseEntity {
    private static final long serialVersionUID = -14419317533629046L;

    @TableId
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "password", title = "密码")
    private String password;

    @Schema(name = "salt", title = "密码盐")
    private String salt;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "birthday", title = "生日")
    private LocalDate birthday;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "email", title = "邮件")
    private String email;

    @Schema(name = "isInitialPassword", title = "是否初始密码:0-不是,1-是,默认1")
    private Integer isInitialPassword;

    @Schema(name = "phone", title = "电话号码")
    private String phone;

    @Schema(name = "currentOrgId", title = "当前登录部门id")
    private String currentOrgId;

    @Schema(name = "orgIds", title = "负责部门")
    private String orgIds;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;

    @Schema(name = "workNo", title = "工号，唯一键")
    private String workNo;

    @Schema(name = "telephone", title = "座机号")
    private String telephone;

    @Schema(name = "loginFailErrorNum", title = "连续登录错误次数")
    private Integer loginFailErrorNum;

    @Schema(name = "currentLoginTime", title = "最近登录时间")
    private LocalDateTime currentLoginTime;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键")
    private String uniqueHelp;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;
}