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
package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 授权客户端信息分页查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacClientDetailsPageDto implements Serializable {
    private static final long serialVersionUID = 735495266631499851L;

    @Schema(name = "clientDetailId", title = "客户端信息id")
    private String clientDetailId;

    @Schema(name = "clientId", title = "客户端id")
    private String clientId;

    @Schema(name = "clientName", title = "客户端名称")
    private String clientName;

    @Schema(name = "clientIco", title = "客户端图标")
    private String clientIco;

    @Schema(name = "signatureRequired", title = "是否验签:0-不验签，1-验签，默认1")
    private Integer signatureRequired;

    @Schema(name = "signatureKey", title = "数据签名key，当需要验签时必填")
    private String signatureKey;

    @Schema(name = "singleLogin", title = "是否单设备登录：0-不是,1-是，默认0")
    private Integer singleLogin;

    @Schema(name = "singleLoginType", title = "单设备登录方式：1-同一用户只能在一个endpoint登录,2-同一用户可以在不同endpoint登录")
    private Integer singleLoginType;

    @Schema(name = "innerSystem", title = "是否内部系统：0-不是，1-是，默认0")
    private Integer innerSystem;

    @Schema(name = "lastAuthTime", title = "上次授权时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime lastAuthTime;

    @Schema(name = "limitError", title = "限制授权错误次数:0-不限制,1-限制。默认0")
    private Integer limitError;

    @Schema(name = "maxErrorNum", title = "允许最大授权错误次数，当限制授权错误时必填")
    private Integer maxErrorNum;

    @Schema(name = "clientStatus", title = "状态：0-未启用,1-启用，2-锁定，默认0")
    private Integer clientStatus;

    @Schema(name = "accessTokenValiditySeconds", title = "访问token的有效时长(单位s)，默认1800秒")
    private Integer accessTokenValiditySeconds;

    @Schema(name = "refreshTokenValiditySeconds", title = "刷新token的有效时长(单位s)，默认604800秒,即7天")
    private Integer refreshTokenValiditySeconds;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "havaScoped", title = "是否领域，0-不是,1-是。默认0")
    private Integer havaScoped;

    @Schema(name = "webRegisteredRedirectUri", title = "授权后跳转的URI（授权码模式必填）")
    private String webRegisteredRedirectUri;

    @Schema(name = "havaAutoApprove", title = "是否自动批准：0-不自动，1-自动,默认0")
    private Integer havaAutoApprove;

    @Schema(name = "codeValiditySeconds", title = "授权码有效时常(单位s)，默认300秒")
    private Integer codeValiditySeconds;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;
}
