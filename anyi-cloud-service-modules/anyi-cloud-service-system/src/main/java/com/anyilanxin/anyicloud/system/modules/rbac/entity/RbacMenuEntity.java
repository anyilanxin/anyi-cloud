/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 菜单表(RbacMenu)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-03 00:29:05
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_menu")
public class RbacMenuEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 391242347439472730L;

    @TableId
    private String menuId;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 前端组件
     */
    private String component;

    /**
     * 路由名称
     */
    private String pathName;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:MenuType
     */
    private Integer menuType;

    /**
     * 是否外连接,实际为boolean
     */
    private boolean iframe;

    /**
     * 外连接类型:0-内嵌,1-外链接
     */
    private Integer iframeType;

    /**
     * 菜单状态:0-禁用,1-启用
     */
    private Integer menuStatus;

    /**
     * 菜单名称
     */
    private String metaTitle;

    /**
     * 是否忽略权限，只在权限模式为Role的时候有效,实际为boolean
     */
    private boolean ignoreAuth;

    /**
     * 是否忽略KeepAlive缓存,实际为boolean
     */
    private boolean ignoreKeepAlive;

    /**
     * 是否固定标签,实际为boolean
     */
    private boolean affix;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)
     */
    private Integer iconType;

    /**
     * 内嵌iframe的地址
     */
    private String frameSrc;

    /**
     * 路由切换的动画名
     */
    private String transitionName;

    /**
     * 隐藏该路由在面包屑上面的显示,实际为boolean
     */
    private boolean hideBreadcrumb;

    /**
     * 是否携带参数并在tab上显示,实际为boolean
     */
    private boolean carryParam;

    /**
     * 隐藏所有子菜单,实际为boolean
     */
    private boolean hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    private String currentActiveMenu;

    /**
     * 当前路由不再标签页显示,实际为boolean
     */
    private boolean hideTab;

    /**
     * 当前路由不再菜单显示,实际为boolean
     */
    private boolean hideMenu;

    /**
     * 菜单排序
     */
    private Integer orderNo;

    /**
     * 忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由,实际为boolean
     */
    private boolean ignoreRoute;

    /**
     * 显示tag,0-不显示,1-显示，实际为boolean
     */
    private boolean showTag;

    /**
     * tag类型：primary、error、warn、success
     */
    private String type;

    /**
     * tag内容
     */
    private String content;

    /**
     * 是否圆点,默认不是,实际为boolean
     */
    private boolean dot;

    /**
     * 是否在子级菜单的完整path中忽略本级path,实际为boolean
     */
    private boolean hidePathForChildren;

    /**
     * 按钮权限标识
     */
    private String buttonActionTag;

    /**
     * 鉴权表达式，不需要鉴权时默认为：permitAll()
     */
    private String buttonExpress;

    /**
     * 所属系统
     */
    private String systemId;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 系统内置编码(系统自动生成)
     */
    private String menuSysCode;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 鉴权指令，只有表达式为非角色是使用
     */
    private String buttonAction;
}
