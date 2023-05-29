

package com.anyilanxin.anyicloud.message.core.handler;

import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.anyicloud.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;

import java.security.Principal;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * @author zxh
 * @date 2022-05-11 22:31
 * @since 1.0.0
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
