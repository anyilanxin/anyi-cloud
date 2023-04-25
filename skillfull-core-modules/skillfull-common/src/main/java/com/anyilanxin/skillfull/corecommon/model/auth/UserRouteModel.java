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
package com.anyilanxin.skillfull.corecommon.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户登录路由信息
 *
 * @author zxiaozhou
 * @date 2021-07-11 20:23
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserRouteModel implements Serializable, Comparable<UserRouteModel> {
    private static final long serialVersionUID = 8153487858620471860L;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "menuSysCode", title = "系统内置编码(系统自动生成)")
    private String menuSysCode;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "component", title = "前端组件")
    private String component;

    @Schema(name = "name", title = "路由名称")
    private String name;

    @Schema(name = "redirect", title = "重定向地址")
    private String redirect;

    @Schema(name = "meta", title = "路由meta信息")
    private UserRouteMetaModel meta;

    @Override
    public int compareTo(UserRouteModel o) {
        return this.getMeta().getOrderNo().compareTo(o.getMeta().getOrderNo());
    }
}
