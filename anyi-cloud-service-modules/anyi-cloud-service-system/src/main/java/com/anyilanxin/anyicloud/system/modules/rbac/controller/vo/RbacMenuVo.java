/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
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
package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 菜单表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-03 00:29:05
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacMenuVo implements Serializable {
    private static final long serialVersionUID = 661431679003980068L;

    @Schema(name = "parentId", title = "父id(编辑时无效)")
    private String parentId;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "component", title = "前端组件")
    private String component;

    @Schema(name = "pathName", title = "路由名称")
    private String pathName;

    @Schema(name = "redirect", title = "重定向地址")
    private String redirect;

    @Schema(name = "menuType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType", required = true)
    @NotBlankOrNull(message = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType不能为空")
    private Integer menuType;

    @Schema(name = "iframe", title = "是否外连接,实际为boolean,默认false")
    private boolean iframe;

    @Schema(name = "iframeType", title = "外连接类型:0-内嵌,1-外链接")
    private Integer iframeType;

    @Schema(name = "frameSrc", title = "内嵌iframe的地址")
    private String frameSrc;

    @Schema(name = "menuStatus", title = "菜单状态:0-禁用,1-启用", required = true)
    @NotBlankOrNull(message = "状态为空")
    @Min(value = 0, message = "状态只能为0、1")
    @Max(value = 1, message = "状态只能为0、1")
    private Integer menuStatus;

    @Schema(name = "metaTitle", title = "菜单名称", required = true)
    @NotBlankOrNull(message = "名称不能为空")
    private String metaTitle;

    @Schema(name = "ignoreKeepAlive", title = "是否忽略KeepAlive缓存,默认false")
    private boolean ignoreKeepAlive;

    @Schema(name = "affix", title = "是否固定标签,默认false")
    private boolean affix;

    @Schema(name = "icon", title = "图标")
    private String icon;

    @Schema(name = "iconType", title = "图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)")
    private Integer iconType;

    @Schema(name = "transitionName", title = "路由切换的动画名")
    private String transitionName;

    @Schema(name = "hideBreadcrumb", title = "隐藏该路由在面包屑上面的显示,默认false")
    private boolean hideBreadcrumb;

    @Schema(name = "carryParam", title = "是否携带参数并在tab上显示,默认false")
    private boolean carryParam;

    @Schema(name = "hideChildrenInMenu", title = "隐藏所有子菜单,默认false")
    private boolean hideChildrenInMenu;

    @Schema(name = "currentActiveMenu", title = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(name = "hideTab", title = "当前路由不再标签页显示,默认false")
    private boolean hideTab;

    @Schema(name = "hideMenu", title = "当前路由不再菜单显示,默认false")
    private boolean hideMenu;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "ignoreRoute", title = "忽略路由。默认false")
    private boolean ignoreRoute;

    @Schema(name = "showTag", title = "显示tag,默认false")
    private boolean showTag;

    @Schema(name = "type", title = "tag类型：primary、error、warn、success")
    private String type;

    @Schema(name = "content", title = "tag内容")
    private String content;

    @Schema(name = "dot", title = "是否圆点,默认false")
    private boolean dot;

    @Schema(name = "hidePathForChildren", title = "是否在子级菜单的完整path中忽略本级path,默认false")
    private boolean hidePathForChildren;

    @Schema(name = "buttonAction", title = "按钮鉴权指令")
    private String buttonAction;

    @Schema(name = "systemId", title = "所属系统", required = true)
    @NotBlankOrNull(message = "所属系统不能为空")
    private String systemId;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
