

package com.anyilanxin.anyicloud.corecommon.constant;

/**
 * 全局公共常量
 *
 * @author zxh
 * @date 2020-07-23 19:57
 * @since 1.0.0
 */
public interface CommonCoreConstant {
    /**
     * 验证动态消息key
     */
    String DYNAMIC_VALIDATE_MESSAGE_KEY = "SKILLFULL_VALIDATE_MESSAGE";

    String CONST_PREFIX = "corecommon_";

    String SKILLFULL_INNTE_SET_TOKEN = "anyi_innte_set_token";

    /**
     * http
     */
    String HTTP = "http://";

    /**
     * https
     */
    String HTTPS = "https://";

    /**
     * 斜杠
     */
    String SLASH = "/";

    /**
     * 冒号
     */
    String COLON = ":";

    /**
     * 横杠
     */
    String DASH = "-";

    /**
     * 空
     */
    String EMPTY = "";

    /**
     * 点
     */
    String POINT = ".";

    /**
     * 时区
     */
    String TIME_ZONE_GMT8 = "GMT+8";

    /**
     * 测试缓存key
     */
    String TEST_DEMO_CACHE = "skillfull:cache";

    /**
     * 开发环境标记
     */
    String DEV = "DEV";

    /**
     * 测试环境标记
     */
    String TEST = "TEST";

    /**
     * 正式环境标记
     */
    String PRO = "PRO";

    /**
     * 雪花终端id
     */
    long WORKER_ID = 12L;

    /**
     * 雪花数据中心id
     */
    long DATACENTER_ID = 12L;
    /**
     * 是否允许灰度调度环境变量key
     */
    String ENABLE_GRAY_KEY = "spring.cloud.nacos.discovery.metadata.gray-info.enable-gray";

    /**
     * 灰度信息key
     */
    String GRAY_HEADER_KEY = "x-gray-info";

    /**
     * 线程池前缀
     */
    String TASK_EXECUTOR_PREFIX = "taskExecutor-";

    String SYSTEM_PREFIX = "SKILL_FULL_";

    /**
     * 跟踪信息
     */
    String X_REQUEST_ID = "x-request-id";

    /**
     * 锁过期时间:单位秒
     */
    long LOCK_EXPIRES = 30;
}
