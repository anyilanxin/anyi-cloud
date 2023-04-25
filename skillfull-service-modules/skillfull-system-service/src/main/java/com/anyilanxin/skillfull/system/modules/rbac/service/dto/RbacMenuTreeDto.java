/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import com.anyilanxin.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限表查询Response
 *
 * @author zxiaozhou
 * @date 2020-10-06 23:14:39
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema
public class RbacMenuTreeDto extends BaseTree<RbacMenuTreeDto> implements Serializable {
    private static final long serialVersionUID = -21159540080828469L;

    @Schema(name = "menuId", title = "菜单id")
    private String menuId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "icon", title = "图标")
    private String icon;

    @Schema(name = "iconType", title = "图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)")
    private Integer iconType;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "menuType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType")
    private Integer menuType;

    @Schema(name = "menuStatus", title = "权限状态:0-停用，1-启用,默认0")
    private Integer menuStatus;
}
