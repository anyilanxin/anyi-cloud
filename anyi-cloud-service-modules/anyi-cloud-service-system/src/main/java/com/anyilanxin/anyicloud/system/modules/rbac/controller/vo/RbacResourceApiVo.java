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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 资源api表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-03 00:29:06
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacResourceApiVo implements Serializable {
    private static final long serialVersionUID = -63465291489451659L;

    @Schema(name = "resourceId", title = "资源id", required = true)
    @NotBlankOrNull(message = "资源id不能为空")
    private String resourceId;

    @Schema(name = "apiUri", title = "接口地址", required = true)
    @NotBlankOrNull(message = "接口地址不能为空")
    private String apiUri;

    @Schema(name = "apiName", title = "接口名称")
    private String apiName;

    @Schema(name = "apiNotes", title = "接口描述")
    private String apiNotes;

    @Schema(name = "requestMethod", title = "请求类型，多个英文逗号隔开", required = true)
    @NotBlankOrNull(message = "请求类型，多个英文逗号隔开不能为空")
    private String requestMethod;

    @Schema(name = "requestParams", title = "请求参数,json object 或json array,保护参数类型")
    private Map<String, Object> requestParams;

    @Schema(name = "responseData", title = "响应参数")
    private Map<String, Object> responseData;

    @Schema(name = "apiTag", title = "api所属分组")
    private String apiTag;

    @Schema(name = "apiTagName", title = "api所属分组名称")
    private String apiTagName;

    @Schema(name = "apiVersions", title = "api版本,多个英文逗号隔开")
    private String apiVersions;

    @Schema(name = "requireAuth", title = "是否鉴权,0-不需要,1-需要。默认1", required = true)
    @NotBlankOrNull(message = "是否鉴权不能为空")
    @Min(value = 0, message = "是否需要鉴权只能为0、1")
    @Max(value = 1, message = "是否需要鉴权只能为0、1")
    private Integer requireAuth;

    @Schema(name = "authType", title = "鉴权类型:1-全局(网关与服务都开启时同时鉴权)，2-网关(紧网关鉴权)，3-服务(网关与服务都开启时紧紧服务鉴权)，默认1,具体与AuthType一致")
    private Integer authType;

    @Schema(name = "permissionExpress", title = "鉴权表达式，不需要鉴权时默认为：permitAll()")
    private String permissionExpress;

    @Schema(name = "permissionAction", title = "按钮鉴权指令")
    private String permissionAction;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
