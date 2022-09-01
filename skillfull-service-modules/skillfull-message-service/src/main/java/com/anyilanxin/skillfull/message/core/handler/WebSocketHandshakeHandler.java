// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.core.handler;

import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author zxiaozhou
 * @date 2022-05-11 22:31
 * @since JDK1.8
 */
@Slf4j
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication principal = context.getAuthentication();
        Object userPrincipal = principal.getPrincipal();
        if (userPrincipal instanceof SkillFullUserDetails) {
            SkillFullUserDetails userDetails = (SkillFullUserDetails) userPrincipal;
            attributes.put(WebSocketSessionType.USER_ID.getType(), userDetails.getUserId());
        }
        if (principal.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails token = (OAuth2AuthenticationDetails) principal.getDetails();
            attributes.put(WebSocketSessionType.TOKEN.getType(), token.getTokenValue());
        }
        attributes.put(WebSocketSessionType.CUSTOM_SESSION_ID.getType(), CoreCommonUtils.getSnowflakeId());
        return principal;
    }


}
