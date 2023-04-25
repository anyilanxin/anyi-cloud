/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

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
 * @author zxiaozhou
 * @date 2022-01-28 09:22
 * @since JDK1.8
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
