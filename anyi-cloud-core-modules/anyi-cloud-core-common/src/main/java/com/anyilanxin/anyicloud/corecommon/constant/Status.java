

package com.anyilanxin.anyicloud.corecommon.constant;

import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 通用状态码
 *
 * @author zxh
 * @date 2020-06-22 16:35
 * @since 1.0.0
 */
@Getter
@ToString
public enum Status {
    // ------------------------成功----------------
    /**
     * 操作成功！
     */
    SUCCESS(0, I18nUtil.get("Status.SUCCESS"), HttpStatus.OK),

    /**
     * 退出成功！
     */
    LOGOUT(0, I18nUtil.get("Status.LOGOUT"), HttpStatus.OK),

    // ------------------------需要重新登录----------------

    /**
     * 登录过期
     */
    TOKEN_EXPIRED(4001, I18nUtil.get("Status.TOKEN_EXPIRED"), HttpStatus.UNAUTHORIZED),

    /**
     * 被提下线
     */
    TOKEN_KICKED_OUT(4002, I18nUtil.get("Status.TOKEN_KICKED_OUT"), HttpStatus.UNAUTHORIZED),

    /**
     * 其他地方登录下线
     */
    TOKEN_LOGIN_ELSEWHERE(4003, I18nUtil.get("Status.TOKEN_LOGIN_ELSEWHERE"), HttpStatus.UNAUTHORIZED),

    // ------------------------操作异常------------------
    /**
     * 操作异常！
     */
    ERROR(5000, I18nUtil.get("Status.ERROR"), HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(4003, I18nUtil.get("Status.ACCESS_DENIED"), HttpStatus.FORBIDDEN),

    /**
     * 授权异常！
     */
    ACCESS_ERROR(4001, I18nUtil.get("Status.ACCESS_ERROR"), HttpStatus.UNAUTHORIZED),

    /**
     * 授权异常！
     */
    ACCESS_INFO_ERROR(4012, I18nUtil.get("Status.ACCESS_ERROR"), HttpStatus.PRECONDITION_FAILED),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(4004, I18nUtil.get("Status.REQUEST_NOT_FOUND"), HttpStatus.NOT_FOUND),

    /**
     * 数据库操作失败
     */
    DATABASE_BASE_ERROR(5000, I18nUtil.get("Status.DATABASE_BASE_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 验证失败
     */
    VERIFICATION_FAILED(5000, I18nUtil.get("Status.VERIFICATION_FAILED"), HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 需要刷新
     */
    NEED_REFRESH(4006, I18nUtil.get("Status.NEED_REFRESH"), HttpStatus.NOT_ACCEPTABLE),

    /**
     * 调用第三方接口失败
     */
    API_ERROR(5003, I18nUtil.get("Status.API_ERROR"), HttpStatus.SERVICE_UNAVAILABLE);

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回信息
     */
    private final String message;

    /**
     * http状态码
     */
    private final HttpStatus status;

    Status(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
