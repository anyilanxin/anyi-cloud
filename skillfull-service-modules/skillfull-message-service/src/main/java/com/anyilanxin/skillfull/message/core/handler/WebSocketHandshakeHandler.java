/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.message.core.handler;

import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
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
* @author zxiaozhou
* @date 2022-05-11 22:31
* @since JDK1.8
*/
@Slf4j
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(
            ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
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
        attributes.put(
                WebSocketSessionType.CUSTOM_SESSION_ID.getType(), CoreCommonUtils.getSnowflakeId());
        return principal;
    }
}
