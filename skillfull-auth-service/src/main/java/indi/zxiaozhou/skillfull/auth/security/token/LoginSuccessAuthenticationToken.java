// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.token;

import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 登录成功授权信息
 *
 * @author zxiaozhou
 * @date 2020-07-17 17:29
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LoginSuccessAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 8281414109044518981L;
    private final CustomUserDetails principal;
    private Object credentials;

    public LoginSuccessAuthenticationToken(CustomUserDetails principal, Object credentials,
                                           Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setDetails(credentials);
        super.setAuthenticated(true);
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }


    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
