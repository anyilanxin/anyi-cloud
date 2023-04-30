/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.auth.oauth2.orror;

import static com.anyilanxin.anyicloud.corecommon.utils.I18nUtil.getLocalMessage;

import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import com.anyilanxin.anyicloud.coremvc.utils.ServletUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

/**
 * 授权异常处理
 *
 * @author 安一老厨
 * @date 2022-02-13 20:54
 * @since 1.0.0
 */
@Slf4j
public class WebOauth2ResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    private static final Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Given client ID does not match authenticated client", "WebOauth2ResponseExceptionTranslator.clientIdNotMatch");
        LOCAL.put("Unauthorized grant type", "WebOauth2ResponseExceptionTranslator.unauthorized");
        LOCAL.put("Unsupported grant type", "WebOauth2ResponseExceptionTranslator.unsupported");
        LOCAL.put("User account is locked", "WebOauth2ResponseExceptionTranslator.accountLocked");
        LOCAL.put("User is disabled", "WebOauth2ResponseExceptionTranslator.accountDisabled");
        LOCAL.put("User account has expired", "WebOauth2ResponseExceptionTranslator.accountExpired");
        LOCAL.put("User credentials have expired", "WebOauth2ResponseExceptionTranslator.accountCredentialsExpired");
        LOCAL.put("Invalid scope", "WebOauth2ResponseExceptionTranslator.invalidScope");
    }

    private static final ThrowableAnalyzer THROWABLEANALYZER = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        HttpServletRequest request = ServletUtils.getRequest();
        Throwable[] causeChain = THROWABLEANALYZER.determineCauseChain(e);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = e.getMessage();
        if (e instanceof OAuth2Exception) {
            OAuth2Exception ase = (OAuth2Exception) THROWABLEANALYZER.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
            status = ase.getHttpErrorCode();
        }
        if (e instanceof UnsupportedGrantTypeException) {
            UnsupportedGrantTypeException grantTypeException = (UnsupportedGrantTypeException) e;
            status = grantTypeException.getHttpErrorCode();
        }
        if (e instanceof InvalidClientException) {
            InvalidClientException invalidClientException = (InvalidClientException) e;
            status = invalidClientException.getHttpErrorCode();
        }
        log.error("------------WebOauth2ResponseExceptionTranslator------------>translate--->异常消息:{}", e.getMessage());
        e.printStackTrace();
        String localMessage = getLocalMessage(LOCAL, message);
        Oauth2LogUtils.setPostAuthLog(false, "", localMessage, null);
        return new ResponseEntity<>(new CustomOauth2Exception(localMessage), HttpStatus.valueOf(status));
    }
}
