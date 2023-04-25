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
package com.anyilanxin.skillfull.system.modules.manage.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* 路由断言(ManageRoutePredicate)Entity
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-04-09 12:02:48
* @since JDK1.8
*/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_manage_route_predicate", autoResultMap = true)
public class ManageRoutePredicateEntity extends BaseEntity {
    private static final long serialVersionUID = 635114207423655775L;

    @TableId private String predicateId;

    /** 服务id */
    private String serviceId;

    /** 路由id */
    private String routeId;

    /** 断言类型 */
    private String predicateType;

    /** 断言类型名称 */
    private String predicateTypeName;

    /** 断言名称 */
    private String predicateName;

    /** 断言规则:Map */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, String> rules;

    /** 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除) */
    private Integer enableDelete;

    /** 备注 */
    private String remark;
}
