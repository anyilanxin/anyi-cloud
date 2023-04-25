package com.anyilanxin.skillfull.oauth2common.tokenstore;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.oauth2common.constant.OAuth2RequestExtendConstant;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * 自定义token key生成
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-09-07 15:58
 * @since JDK11
 */
public class CustomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {
    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";
    public static final String LOGIN_UNIQUE = OAuth2RequestExtendConstant.LOGIN_UNIQUE;
    public static final String LOGIN_ENDPOINT = OAuth2RequestExtendConstant.LOGIN_ENDPOINT;
    public static final String LIMIT_RESOURCE = OAuth2RequestExtendConstant.LIMIT_RESOURCE;


    public CustomAuthenticationKeyGenerator() {
    }

    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap<>();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            values.put(USERNAME, authentication.getName());
        }
        Map<String, Serializable> extensions = authentication.getOAuth2Request().getExtensions();
        values.put(CLIENT_ID, authorizationRequest.getClientId());
        if (authorizationRequest.getScope() != null) {
            values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<>(authorizationRequest.getScope())));
        }
        if (CollectionUtil.isNotEmpty(extensions)) {
            Serializable serializable = extensions.get(LOGIN_UNIQUE);
            if (Objects.nonNull(serializable)) {
                values.put(LOGIN_UNIQUE, serializable.toString());
            }
            serializable = extensions.get(LOGIN_ENDPOINT);
            if (Objects.nonNull(serializable)) {
                values.put(LOGIN_ENDPOINT, serializable.toString());
            }
            serializable = extensions.get(LIMIT_RESOURCE);
            if (Objects.nonNull(serializable)) {
                values.put(LIMIT_RESOURCE, serializable.toString());
            }
        }
        return this.generateKey(values);
    }

    protected String generateKey(Map<String, String> values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException var4) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", var4);
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", var5);
        }
    }
}
