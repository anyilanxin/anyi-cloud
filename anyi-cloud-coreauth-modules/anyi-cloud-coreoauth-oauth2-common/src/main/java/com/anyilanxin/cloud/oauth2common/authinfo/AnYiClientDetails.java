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

package com.anyilanxin.cloud.oauth2common.authinfo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 客户端信息
 *
 * @author zxh
 * @date 2022-02-14 17:08
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
public class AnYiClientDetails {
    /**
     * 客户端id
     */

    private String clientId;
    /**
     * 限制授权资源：0-不限制，1-限制。默认1
     */
    private Integer limitResource;

    /**
     * 客户端名称
     */
    private String clientName;
    /**
     * 授权资源
     */
    private Set<String> resourceIds;
    /**
     * 客户端是有要密码
     */
    private boolean secretRequired;
    /**
     * 客户端密码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String clientSecret;
    /**
     * 是否领域
     */
    private boolean scoped;
    /**
     * 授权领域信息
     */
    private Set<String> scope;
    /**
     * 授权支持类型
     */
    private Set<String> authorizedGrantTypes;
    /**
     * 授权码回调地址
     */
    private Set<String> registeredRedirectUri;
    /**
     * 授权信息
     */
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * token有效期(s)
     */
    private Integer accessTokenValiditySeconds;
    /**
     * 刷新token有效期(s)
     */
    private Integer refreshTokenValiditySeconds;
    /**
     * 是否自动同意
     */
    private boolean autoApprove;
    /**
     * 附加信息
     */
    private Map<String, Object> additionalInformation;
    /**
     * 限制授权错误次数:0-不限制,1-限制。默认0
     */
    private Integer limitError;
    /**
     * 允许最大授权错误次数
     */
    private Integer maxErrorNum;
    /**
     * 状态
     */
    private Integer clientStatus;
    /**
     * 授权码有效时常(单位s)，默认300秒
     */
    private Integer codeValiditySeconds;

    /**
     * 是否单设备登录：0-不是,1-是，默认0
     */
    private Integer singleLogin;

    /**
     * 当前角色编码信息
     */
    private Set<String> roleCodes;

    /**
     * 当前角色id
     */
    private Set<String> roleIds;

    /**
     * 当前角色信息
     */
    private Set<RoleInfo> roleInfo;

    /**
     * 单设备登录方式：1-同一用户只能在一个endpoint登录,2-同一用户可以在不同endpoint登录
     */
    private Integer singleLoginType;

}
