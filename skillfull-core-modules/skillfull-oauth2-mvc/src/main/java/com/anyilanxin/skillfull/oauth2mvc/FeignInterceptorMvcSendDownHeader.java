package com.anyilanxin.skillfull.oauth2mvc;


import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
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
@Order(Integer.MIN_VALUE)
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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails token = (OAuth2AuthenticationDetails) authentication.getDetails();
                template.header(AuthConstant.BEARER_TOKEN_HEADER_NAME, "Bearer " + token.getTokenValue());
            }
        } catch (Exception e) {
            log.error("------------------设置token下传失败------apply--->{}", e.getMessage());
        }

    }
}
