/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 角色菜单按钮
 *
 * @author zxh
 * @date 2022-01-28 09:22
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacRoleMenuButtonDto implements Serializable {
    private static final long serialVersionUID = -63399996952662860L;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "superRole", title = "是否为超级管理员角色")
    private boolean superRole;

    @Schema(name = "roleSysCode", title = "角色系统编码(系统自动创建)")
    private String roleSysCode;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "actionSet", title = "菜单按钮权限")
    private Set<Action> actionSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RbacRoleMenuButtonDto)) {
            return false;
        }
        RbacRoleMenuButtonDto that = (RbacRoleMenuButtonDto) o;
        return Objects.equals(getMenuId(), that.getMenuId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getMenuId());
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema
    public static class Action implements Serializable {
        private static final long serialVersionUID = 6588630894991792617L;

        @Schema(name = "menuId", title = "权限id")
        private String menuId;

        @Schema(name = "actions", title = "按钮权限标识")
        private String action;

        @Schema(name = "roleId", title = "角色id")
        private String roleId;

        @Schema(name = "actionType", title = "按钮权限类型:1-前端型,2-后端型,3-前后端型")
        private Integer actionType;

        @Schema(name = "metaTitle", title = "菜单名称")
        private String metaTitle;

        @Schema(name = "parentId", title = "父id")
        private String parentId;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Action)) {
                return false;
            }
            Action action = (Action) o;
            return Objects.equals(getMenuId(), action.getMenuId());
        }


        @Override
        public int hashCode() {
            return Objects.hash(getMenuId());
        }
    }
}
