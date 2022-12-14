package com.anyilanxin.skillfull.auth.oauth2.config;

import com.anyilanxin.skillfull.auth.core.properties.AuthProperty;
import com.anyilanxin.skillfull.auth.modules.detail.servive.impl.JdbcClientDetailsService;
import com.anyilanxin.skillfull.auth.modules.detail.servive.impl.OpenIdDetailsService;
import com.anyilanxin.skillfull.auth.modules.detail.servive.impl.PasswordDetailsService;
import com.anyilanxin.skillfull.auth.modules.detail.servive.impl.PhoneDetailsService;
import com.anyilanxin.skillfull.auth.oauth2.CustomDefaultTokenServices;
import com.anyilanxin.skillfull.auth.oauth2.CustomTokenApprovalStore;
import com.anyilanxin.skillfull.auth.oauth2.CustomTokenEnhancer;
import com.anyilanxin.skillfull.auth.oauth2.filter.CustomAuthenticationFailureHandler;
import com.anyilanxin.skillfull.auth.oauth2.filter.CustomAuthenticationSuccessHandler;
import com.anyilanxin.skillfull.auth.oauth2.filter.CustomClientCredentialsTokenEndpointFilter;
import com.anyilanxin.skillfull.auth.oauth2.granter.*;
import com.anyilanxin.skillfull.auth.oauth2.orror.WebOauth2ResponseExceptionTranslator;
import com.anyilanxin.skillfull.auth.oauth2.provider.ClientCredentialsAuthenticationProvider;
import com.anyilanxin.skillfull.auth.oauth2.provider.OpenIdAuthenticationProvider;
import com.anyilanxin.skillfull.auth.oauth2.provider.PictureCodeAuthenticationProvider;
import com.anyilanxin.skillfull.auth.oauth2.provider.SmsCodeAuthenticationProvider;
import com.anyilanxin.skillfull.auth.oauth2.store.code.RedisAuthorizationCodeServices;
import com.anyilanxin.skillfull.auth.oauth2.validate.impl.PictureValidate;
import com.anyilanxin.skillfull.auth.oauth2.validate.impl.SmsValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ????????????????????????
 *
 * @author zhouxuanhong
 * @date 2019-05-19 15:29
 * @since JDK1.8
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    private final JdbcClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AuthProperty property;
    private final OpenIdDetailsService openIdDetailsService;
    private final PasswordDetailsService passwordDetailsService;
    private final PictureValidate pictureValidate;
    private final PhoneDetailsService phoneDetailsService;
    private final SmsValidate smsValidate;
    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenConverter tokenConverter;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        endpointFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        endpointFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        security.addTokenEndpointAuthenticationFilter(endpointFilter);
        security.tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(List.of(customTokenEnhancer()));
        endpoints
                .approvalStore(approvalStore())
                .authenticationManager(authenticationManager)
                .approvalStore(approvalStore())
                .tokenServices(tokenServices())
                .approvalStoreDisabled()
                .pathMapping("/oauth/authorize", "/oauth/code")
                // ????????????????????????
//                .pathMapping("/oauth/access_token", "/oauth/access-token")//AuthorizationServerSecurityConfiguration
                // ?????????????????????????????????
                .pathMapping("/oauth/confirm_access", "/oauth/confirm-access")
                // ???????????????????????????????????????
                .pathMapping("/oauth/error", "/oauth/error")
                // ???????????????????????????access token(?????????JWT?????????????????????????????????)??????
                .pathMapping("/oauth/check_token", "/oauth/check-token")
                .tokenGranter(getTokenGranters(endpoints))
                // ???????????????????????????
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                .tokenStore(tokenStore)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenEnhancer(tokenEnhancer())
                .exceptionTranslator(new WebOauth2ResponseExceptionTranslator());
    }


    /**
     * ??????grant_type??????
     *
     * @return TokenGranter ${@link TokenGranter}
     * @author zhouxuanhong
     * @date 2019-06-04 15:08
     */
    private TokenGranter getTokenGranters(AuthorizationServerEndpointsConfigurer endpoints) {
        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
        ClientDetailsService clientDetailsService = endpoints.getClientDetailsService();
        OAuth2RequestFactory oAuth2RequestFactory = endpoints.getOAuth2RequestFactory();
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // ?????????
        tokenGranters.add(new AuthCodeTokenGranter(
                tokenServices,
                endpoints.getAuthorizationCodeServices(),
                clientDetailsService,
                oAuth2RequestFactory
        ));
        // ?????????
        ClientCredentialsAuthenticationProvider clientCredentialsAuthenticationProvider = new ClientCredentialsAuthenticationProvider(clientDetailsService, passwordEncoder);
        clientCredentialsAuthenticationProvider.setHideUserNotFoundExceptions(false);
        tokenGranters.add(new ClientCredentialsTokenGranter(
                clientCredentialsAuthenticationProvider::authenticate,
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        // ??????id
        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider(openIdDetailsService);
        openIdAuthenticationProvider.setHideUserNotFoundExceptions(false);
        tokenGranters.add(new OpenIdTokenGranter(
                openIdAuthenticationProvider::authenticate,
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        // ???????????????
        PictureCodeAuthenticationProvider pictureCodeAuthenticationProvider = new PictureCodeAuthenticationProvider(passwordDetailsService, passwordEncoder, pictureValidate);
        pictureCodeAuthenticationProvider.setHideUserNotFoundExceptions(false);
        tokenGranters.add(new PictureCodeTokenGranter(
                pictureCodeAuthenticationProvider::authenticate,
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        // ???????????????
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider(phoneDetailsService, smsValidate);
        smsCodeAuthenticationProvider.setHideUserNotFoundExceptions(false);
        tokenGranters.add(new SmsCodeTokenGranter(
                smsCodeAuthenticationProvider::authenticate,
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        // ??????token
        tokenGranters.add(new RefreshTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        return new CompositeTokenGranter(tokenGranters);
    }

    @Bean
    public ApprovalStore approvalStore() {
        CustomTokenApprovalStore tokenApprovalStore = new CustomTokenApprovalStore();
        tokenApprovalStore.setTokenStore(tokenStore);
        return tokenApprovalStore;
    }


    /**
     * ???????????????
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        RedisAuthorizationCodeServices codeServices = new RedisAuthorizationCodeServices(redisTemplate);
        codeServices.setValiditySeconds(10 * 60);
        return codeServices;
    }


    /**
     * token?????????
     */
    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return new CustomTokenEnhancer();
    }


    /**
     * token????????????(?????????????????????????????????)
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), accessTokenConverter()));
        return tokenEnhancerChain;
    }

    /**
     * token??????
     *
     * @return DefaultTokenServices ${@link DefaultTokenServices}
     * @author zhouxuanhong
     * @date 2019-05-31 16:28???
     */
    @Bean
    public CustomDefaultTokenServices tokenServices() {
        CustomDefaultTokenServices customDefaultTokenServices = new CustomDefaultTokenServices();
        customDefaultTokenServices.setClientDetailsService(clientDetailsService);
        customDefaultTokenServices.setTokenStore(tokenStore);
        customDefaultTokenServices.setAuthProperty(property);
        customDefaultTokenServices.setTokenEnhancer(tokenEnhancer());
        customDefaultTokenServices.setApprovalStore(approvalStore());
        return customDefaultTokenServices;
    }


    /**
     * token?????????????????????
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setAccessTokenConverter(tokenConverter);
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }


    @Bean
    public KeyPair keyPair() {
        // ???classpath??????????????????????????????
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "654321".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "654321".toCharArray());
    }
}
