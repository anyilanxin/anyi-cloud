// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Feign拦截器
 *
 * @author zxiaozhou
 * @date 2019-04-24 14:19
 * @since JDK11
 */
@Component
@Slf4j
public class FeignInterceptorMvcSendDownHeader implements RequestInterceptor {

    /**
     * token设置
     *
     * @param template ${@link RequestTemplate}
     * @author zxiaozhou
     * @date 2019-05-15 17:52
     */
    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails token = (OAuth2AuthenticationDetails) authentication.getDetails();
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getTokenValue());
        }
    }
}
