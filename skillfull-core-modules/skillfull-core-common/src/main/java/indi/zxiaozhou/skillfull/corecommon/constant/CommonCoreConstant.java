// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.constant;

/**
 * 全局公共常量
 *
 * @author zxiaozhou
 * @date 2020-07-23 19:57
 * @since JDK11
 */
public interface CommonCoreConstant {
    String CONST_PREFIX = "corecommon_";

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
    String TEST_DEMO_CACHE = "test:demo";

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
