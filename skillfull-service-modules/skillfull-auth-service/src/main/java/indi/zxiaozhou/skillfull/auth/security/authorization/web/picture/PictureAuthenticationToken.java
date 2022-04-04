// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.web.picture;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 图片验证码token
 *
 * @author zxiaozhou
 * @date 2020-06-29 02:44
 * @since JDK11
 */
@Getter
public class PictureAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 7586406591852594067L;

    private final Object principal;
    private Object credentials;
    private Object validate;

    public PictureAuthenticationToken(Object principal, Object credentials, Object validate) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.validate = validate;
        setAuthenticated(false);
    }


    public PictureAuthenticationToken(Object principal, Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        // must use super, as we override
        super.setAuthenticated(true);
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }


    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
