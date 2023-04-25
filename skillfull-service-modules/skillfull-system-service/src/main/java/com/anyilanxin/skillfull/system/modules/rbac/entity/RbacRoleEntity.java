/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* 角色表(RbacRole)Entity
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
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

    @TableId private String roleId;

    /** 角色名称 */
    private String roleName;

    /** 角色系统编码(系统自动创建) */
    private String roleSysCode;

    /** 数据权限类型：1-全部,2-当前机构,3-机构及以下,4-机构自定义,5-当前区域,6-区域及以下,7-区域自定义,8-仅自己 */
    private Integer dataAuthType;

    /** 自定义类数据权限数据 */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> customDataAuthData;

    /** 角色编码 */
    private String roleCode;

    /** 上级角色id */
    private String parentRoleId;

    /** 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除) */
    private Integer enableDelete;

    /** 绑定方式:0-手动,1-自动。默认0(用户创建时自动挂接) */
    private Integer autoBind;

    /** 角色状态:0-禁用,1-启用,默认0 */
    private Integer roleStatus;

    /** 备注 */
    private String remark;

    /** 唯一索引帮助字段,默认1，如果删除该值为主键 */
    private String uniqueHelp;
}
