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

package com.anyilanxin.anyicloud.auth.modules.login.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 资源api表分页查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-03 00:29:07
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacResourceApiSimpleDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 578791871366557638L;

    @Schema(name = "apiId", title = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值")
    private String apiId;

    @Schema(name = "resourceId", title = "资源id")
    private String resourceId;

    @Schema(name = "resourceCode", title = "资源编码,即后端服务名")
    private String resourceCode;

    @Schema(name = "permissionExpress", title = "鉴权表达式，不需要鉴权时默认为：permitAll()")
    private String permissionExpress;

    @Schema(name = "permissionAction", title = "鉴权指令，只有表达式为非角色是使用")
    private String permissionAction;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RbacResourceApiSimpleDto))
            return false;

        RbacResourceApiSimpleDto that = (RbacResourceApiSimpleDto) o;

        if (getResourceCode() != null ? !getResourceCode().equals(that.getResourceCode()) : that.getResourceCode() != null)
            return false;
        return getPermissionAction() != null ? getPermissionAction().equals(that.getPermissionAction()) : that.getPermissionAction() == null;
    }


    @Override
    public int hashCode() {
        int result = getResourceCode() != null ? getResourceCode().hashCode() : 0;
        result = 31 * result + (getPermissionAction() != null ? getPermissionAction().hashCode() : 0);
        return result;
    }
}
