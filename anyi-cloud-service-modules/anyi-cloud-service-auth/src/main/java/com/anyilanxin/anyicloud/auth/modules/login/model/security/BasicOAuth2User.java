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
package com.anyilanxin.anyicloud.auth.modules.login.model.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 基础OAuth2用户信息类
 *
 * @author vains
 */
@Data
@JsonSerialize
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicOAuth2User implements OAuth2User, Serializable {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 三方登录唯一id
     */
    private String uniqueId;

    /**
     * 三方登录用户名
     */
    private String thirdUsername;

    /**
     * 三方登录获取的认证信息
     */
    private String credentials;

    /**
     * 三方登录获取的认证信息的过期时间
     */
    private LocalDateTime credentialsExpiresAt;

    /**
     * 三方登录类型
     */
    private String type;

    /**
     * 博客地址
     */
    private String blog;

    /**
     * 网格地址
     */
    private String location;


    /**
     * 用户表主键
     */
    private Integer userId;

    /**
     * 绑定时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户名、昵称
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 用户来源
     */
    private String sourceFrom;


    /**
     * 三方账号字段名
     */
    private String nameAttributeKey;

    /**
     * 三方用户属性信息
     */
    private Map<String, Object> attributes;

    /**
     * 三方用户权限信息(scope)
     */
    private Set<GrantedAuthority> authorities;

    @Override
    public String getName() {
        return attributes.get(nameAttributeKey) + "";
    }

    public BasicOAuth2User(OAuth2User oAuth2User, String nameAttributeKey) {
        Assert.notEmpty(oAuth2User.getAttributes(), "attributes cannot be empty");
        this.authorities = (oAuth2User.getAttributes() != null)
                ? Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(oAuth2User.getAuthorities())))
                : Collections.unmodifiableSet(new LinkedHashSet<>(AuthorityUtils.NO_AUTHORITIES));
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(oAuth2User.getAttributes()));
        this.nameAttributeKey = nameAttributeKey;
    }

    /**
     * 将权限排序
     *
     * @param authorities 权限
     * @return 排序后的权限信息
     */
    private Set<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
                Comparator.comparing(GrantedAuthority::getAuthority));
        sortedAuthorities.addAll(authorities);
        return sortedAuthorities;
    }

}
