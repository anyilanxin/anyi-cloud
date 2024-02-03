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

package com.anyilanxin.anyicloud.corecommon.model.system;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.ClientBaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 授权客户端信息查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-02-12 14:44:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ClientAndResourceAuthModel extends ClientBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 574726562345912051L;

    @Schema(title = "id")
    private String id;

    @Schema(title = "客户端id")
    private String clientId;

    @Schema(title = "客户端名称")
    private String clientName;

    @Schema(title = "客户端图标")
    private String clientIco;

    @Schema(title = "客户端密码")
    private String clientSecurity;

    @Schema(title = "客户端签发时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime clientIdIssuedAt;

    @Schema(title = "客户端签密钥过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime clientSecretExpiresAt;

    @Schema(title = "上次授权时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime lastAuthTime;

    @Schema(title = "限制授权错误次数:0-不限制,1-限制。默认0")
    private Integer limitError;

    @Schema(title = "允许最大授权错误次数，当限制授权错误时必填")
    private Integer maxErrorNum;

    @Schema(title = "授权模式:0-宽松模式，1-严格模式,默认1")
    private Integer authMode;

    @Schema(title = "状态：0-未启用,1-启用，2-锁定，默认0")
    private Integer clientStatus;

    @Schema(name = "additionalInformation", title = "扩展信息,json object")
    private Map<String, Object> additionalInformation;

    @Schema(name = "roleInfos", title = "当前角色信息")
    private Set<RoleInfo> roleInfos;

    @Schema(name = "roleCodes", title = "当前角色编码信息")
    private Set<String> roleCodes;

    @Schema(name = "roleIds", title = "当前角色id信息")
    private Set<String> roleIds;

    @Schema(name = "actions", title = "按钮权限")
    private Map<String, Set<String>> actions;
}
