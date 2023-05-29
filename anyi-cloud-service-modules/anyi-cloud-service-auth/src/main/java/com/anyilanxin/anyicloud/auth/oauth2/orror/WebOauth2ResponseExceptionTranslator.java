

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
 * @author zxh
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
