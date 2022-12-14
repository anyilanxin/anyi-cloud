// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.login.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户表(RbacUser)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class RbacUserDto implements Serializable {
    private static final long serialVersionUID = -40613723241141610L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 开放id
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 个人简介
     */
    private String shortProfile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    /**
     * 性别:0-默认未知,1-男,2-女,默认0
     */
    private Integer sex;

    /**
     * 邮件
     */
    private String email;

    /**
     * 是否初始密码:0-不是,1-是,默认1
     */
    private Integer isInitialPassword;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 来源
     */
    private String registerSource;

    /**
     * 状态:0-未激活,1-正常,2-冻结,默认1
     */
    private Integer userStatus;

    /**
     * 工号，唯一键
     */
    private String workNo;

    /**
     * 座机号
     */
    private String telephone;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 扩展信息,json object
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 职位,org_id array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> positionIds;

    /**
     * 负责机构,org_id array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> managerOrgIds;

    /**
     * 连续登录错误次数
     */
    private Integer loginFailErrorNum;

    /**
     * 当前登录部门id
     */
    private String currentOrgId;

    /**
     * 最后登录IP
     */
    private String currentLoginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentLoginDate;

    /**
     * 备注
     */
    private String remark;
}
