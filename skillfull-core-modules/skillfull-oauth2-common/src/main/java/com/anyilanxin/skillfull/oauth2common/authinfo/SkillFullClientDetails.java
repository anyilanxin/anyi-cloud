package com.anyilanxin.skillfull.oauth2common.authinfo;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * 客户端信息
 *
 * @author zxiaozhou
 * @date 2022-02-14 17:08
 * @since JDK1.8
 */
@Setter
@Getter
@ToString
public class SkillFullClientDetails implements ClientDetails {
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

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return StringUtils.isNotBlank(this.clientSecret);
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scoped;
    }

    @Override
    public Set<String> getScope() {
        if (CollectionUtil.isEmpty(this.scope)) {
            return Collections.emptySet();
        }
        return this.scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUri;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (CollectionUtil.isEmpty(this.authorities)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.authorities);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return this.autoApprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }
}
