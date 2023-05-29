

package com.anyilanxin.anyicloud.logging.core.constant;

/**
 * 日志服务常量
 *
 * @author zxh
 * @date 2022-08-13 11:04
 * @since 1.0.0
 */
public interface LoggingCommonConstant {
    /**
     * redis key前缀
     */
    String REDIS_KEY_PREFIX = "logging-service:";

    /**
     * 授权日志前缀
     */
    String AUTH_LOG_KEY_PREFIX = REDIS_KEY_PREFIX + "AUTH_LOG:";

    /**
     * 操作日志前缀
     */
    String OPERATE_LOG_KEY_PREFIX = REDIS_KEY_PREFIX + "OPERATE_LOG:";
}
