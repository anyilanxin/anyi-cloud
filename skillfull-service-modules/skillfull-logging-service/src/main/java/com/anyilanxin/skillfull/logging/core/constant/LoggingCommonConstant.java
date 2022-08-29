// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.core.constant;

/**
 * 日志服务常量
 *
 * @author zxiaozhou
 * @date 2022-08-13 11:04
 * @since JDK11
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
