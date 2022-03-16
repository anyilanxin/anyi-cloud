// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import indi.zxiaozhou.skillfull.auth.core.constant.impl.LoginType;
import indi.zxiaozhou.skillfull.auth.utils.LoginUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.LoginEndpointType;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * security user detail
 *
 * @author zxiaozhou
 * @date 2020-06-28 12:19
 * @since JDK11
 */
@Getter
@ToString(callSuper = true)
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 704418727943564609L;

    private final LoginOnlineInfoModel onlineInfoModel;

    private final LoginUserInfoModel userInfoModel;

    private final LoginTokenSecurityModel securityModel;

    public CustomUserDetails(LoginEndpointType loginEndpointType,
                             LoginUserInfoModel userInfoModel,
                             long tokenDetectInSeconds,
                             long tokenValidityInSeconds,
                             long tokenRenewInSeconds,
                             LoginType loginType) {
        this.onlineInfoModel = LoginUtil.createOnline(loginEndpointType);
        this.onlineInfoModel.setUserId(userInfoModel.getUserId());
        String loginCode = CoreCommonUtils.getSnowflakeId();
        if (LoginType.ONE_EQUIPMENT == loginType) {
            loginCode = userInfoModel.getUserId();
        }
        this.onlineInfoModel.setLoginType(loginType.getType());
        this.onlineInfoModel.setLoginCode(loginCode);
        this.userInfoModel = userInfoModel;
        LoginTokenSecurityModel tokenSecurity = LoginUtil.createTokenSecurity();
        tokenSecurity.setTokenDetectInSeconds(tokenDetectInSeconds);
        tokenSecurity.setTokenValidityInSeconds(tokenValidityInSeconds);
        tokenSecurity.setTokenRenewInSeconds(tokenRenewInSeconds);
        this.securityModel = tokenSecurity;
    }

    public CustomUserDetails(LoginOnlineInfoModel onlineInfoModel, LoginUserInfoModel userInfoModel, LoginTokenSecurityModel securityModel) {
        this.onlineInfoModel = onlineInfoModel;
        this.userInfoModel = userInfoModel;
        this.securityModel = securityModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    @JsonIgnore
    public String getPassword() {
        return this.userInfoModel.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.userInfoModel.getUserName();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.userInfoModel.getUserStatus() == 2;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.userInfoModel.getUserStatus() == 1;
    }
}
