package com.anyilanxin.skillfull.auth.oauth2.store.code;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存存储code
 *
 * @author zxiaozhou
 * @date 2022-02-13 01:11
 * @since JDK1.8
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
