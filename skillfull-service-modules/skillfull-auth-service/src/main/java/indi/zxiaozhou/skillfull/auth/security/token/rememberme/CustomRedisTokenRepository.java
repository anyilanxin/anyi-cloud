// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.token.rememberme;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * redis
 *
 * @author zxiaozhou
 * @date 2020-07-17 11:34
 * @since JDK11
 */
public class CustomRedisTokenRepository implements PersistentTokenRepository {
    public CustomRedisTokenRepository() {

    }

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {

    }

    @Override
    public void updateToken(String s, String s1, Date date) {

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String s) {
        return null;
    }

    @Override
    public void removeUserTokens(String s) {

    }
}
