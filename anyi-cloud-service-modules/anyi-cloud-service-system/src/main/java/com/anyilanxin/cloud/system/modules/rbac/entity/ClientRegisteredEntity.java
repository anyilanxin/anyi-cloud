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

package com.anyilanxin.cloud.system.modules.rbac.entity;

import com.anyilanxin.cloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Map;

import static com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 授权客户端信息(ClientRegistered)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-09-24 14:48:15
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_rbac_client_registered", autoResultMap = true)
public class ClientRegisteredEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -81933449984809221L;

    @TableId
    private String id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端图标
     */
    private String clientIco;

    /**
     * 客户端密码
     */
    private String clientSecurity;

    /**
     * 允许授权类型，来源与授权中心常量字典AuthorizedGrantType,json array
     */
    private String authorizationGrantTypes;

    /**
     * 允许授权方法，具体与ClientAuthenticationMethod一致
     */
    private String clientAuthenticationMethods;

    /**
     * 客户端签发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端签密钥过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端配置信息
     */
    private String clientSettings;

    /**
     * 推出登录后重定向url地址
     */
    private String postLogoutRedirectUris;

    /**
     * 登录重定向地址
     */
    private String redirectUris;

    /**
     * 授权领域
     */
    private String scopes;

    /**
     * token配置
     */
    private String tokenSettings;

    /**
     * 上次授权时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime lastAuthTime;

    /**
     * 限制授权错误次数:0-不限制,1-限制。默认0
     */
    private Integer limitError;

    /**
     * 授权模式:0-宽松模式，1-严格模式,默认1
     */
    private Integer authMode;

    /**
     * 允许最大授权错误次数，当限制授权错误时必填
     */
    private Integer maxErrorNum;

    /**
     * 状态：0-未启用,1-启用，2-锁定，默认0
     */
    private Integer clientStatus;

    /**
     * 扩展信息,json object
     */
    @TableField(typeHandler = Fastjson2TypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
