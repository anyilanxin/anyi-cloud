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

package com.anyilanxin.anyicloud.corecommon.model.auth;

import com.anyilanxin.anyicloud.corecommon.model.system.RouteTagModel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 路由meta
 *
 * @author zxh
 * @date 2021-06-04 00:17
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Schema
public class UserRouteMetaModel implements Serializable {
    private static final long serialVersionUID = -3148648474688359998L;

    @Schema(name = "title", title = "路由title")
    private String title;

    @Schema(name = "ignoreAuth", title = "是否忽略权限")
    private boolean ignoreAuth;

    @Schema(name = "ignoreKeepAlive", title = "是否固定标签,实际为boolean,实际为boolean")
    private boolean ignoreKeepAlive;

    @Schema(name = "affix", title = "是否固定标签")
    private boolean affix;

    @Schema(name = "icon", title = "图标")
    private String icon;

    @Schema(name = "iconType", title = "图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)")
    private Integer iconType;

    @Schema(name = "frameSrc", title = "内嵌iframe的地址")
    private String frameSrc;

    @Schema(name = "transitionName", title = "路由切换的动画名")
    private String transitionName;

    @Schema(name = "hideBreadcrumb", title = "隐藏该路由在面包屑上面的显示,实际为boolean")
    private boolean hideBreadcrumb;

    @Schema(name = "carryParam", title = "是否携带参数并在tab上显示,实际为boolean")
    private boolean carryParam;

    @Schema(name = "hideChildrenInMenu", title = "隐藏所有子菜单,实际为boolean")
    private boolean hideChildrenInMenu;

    @Schema(name = "currentActiveMenu", title = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(name = "hideTab", title = "当前路由不再标签页显示,实际为boolean")
    private boolean hideTab;

    @Schema(name = "hideMenu", title = "当前路由不再菜单显示,实际为boolean")
    private boolean hideMenu;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "ignoreRoute", title = "忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由,实际为boolean")
    private boolean ignoreRoute;

    @Schema(name = "hidePathForChildren", title = "是否在子级菜单的完整path中忽略本级path,实际为boolean")
    private boolean hidePathForChildren;

    @Schema(name = "actionSet", title = "授权指令")
    private Set<String> actionSet;

    @Schema(name = "tag", title = "路由tag信息")
    private RouteTagModel tag;
}
