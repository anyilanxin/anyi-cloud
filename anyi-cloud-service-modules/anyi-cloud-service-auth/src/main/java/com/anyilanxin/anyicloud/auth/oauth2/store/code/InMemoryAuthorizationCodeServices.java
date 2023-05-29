

package com.anyilanxin.anyicloud.auth.oauth2.store.code;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * 内存存储code
 *
 * @author zxh
 * @date 2022-02-13 01:11
 * @since 1.0.0
 */
public class InMemoryAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    protected final ConcurrentHashMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<>();

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        this.authorizationCodeStore.put(code, authentication);
    }


    @Override
    public OAuth2Authentication remove(String code) {
        return this.authorizationCodeStore.remove(code);
    }
}
