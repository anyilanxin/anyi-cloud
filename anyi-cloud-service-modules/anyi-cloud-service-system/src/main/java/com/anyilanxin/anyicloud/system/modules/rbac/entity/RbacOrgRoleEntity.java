

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构角色表(RbacOrgRole)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_rbac_org_role", autoResultMap = true)
public class RbacOrgRoleEntity extends BaseEntity {
    private static final long serialVersionUID = 649481080764258729L;

    @TableId
    private String orgRoleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,6-仅自己
     */
    private Integer dataAuthType;

    /**
     * 自定义类角色数据权限,权限ids json array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> customDataAuthData;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 绑定方式:0-手动,1-自动。默认0，挂接机构时自动挂接
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

    /**
     * 机构id
     */
    private String orgId;
}
