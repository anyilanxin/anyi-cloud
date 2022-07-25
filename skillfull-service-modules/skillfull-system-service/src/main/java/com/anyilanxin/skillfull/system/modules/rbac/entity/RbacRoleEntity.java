// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * 角色表(RbacRole)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 19:29:58
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_rbac_role", autoResultMap = true)
public class RbacRoleEntity extends BaseEntity {
    private static final long serialVersionUID = -52197794991227239L;

    @TableId
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色系统编码(系统自动创建)
     */
    private String roleSysCode;

    /**
     * 数据权限类型：1-全部,2-当前机构,3-机构及以下,4-机构自定义,5-当前区域,6-区域及以下,7-区域自定义,8-仅自己
     */
    private Integer dataAuthType;

    /**
     * 自定义类数据权限数据
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> customDataAuthData;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 上级角色id
     */
    private String parentRoleId;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 绑定方式:0-手动,1-自动。默认0(用户创建时自动挂接)
     */
    private Integer autoBind;

    /**
     * 角色状态:0-禁用,1-启用,默认0
     */
    private Integer roleStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;
}
