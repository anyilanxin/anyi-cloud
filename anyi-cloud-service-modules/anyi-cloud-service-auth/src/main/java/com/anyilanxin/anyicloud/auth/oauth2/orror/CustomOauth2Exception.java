

package com.anyilanxin.anyicloud.auth.oauth2.orror;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * OAuth2Exception异常类
 *
 * @author zhouxuanhong
 * @date 2019-05-27 16:28
 * @since 1.0.0
 */
@JsonSerialize(using = CustomExceptionJacksonSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {
    private final Result<T> error;

    public CustomOauth2Exception(String msg) {
        super(msg);
        this.error = new Result<>(Status.ACCESS_ERROR, msg);
    }


    public CustomOauth2Exception(Result<T> error) {
        super(error.getMessage());
        this.error = error;
    }


    public Result<T> getResult() {
        return this.error;
    }
}
