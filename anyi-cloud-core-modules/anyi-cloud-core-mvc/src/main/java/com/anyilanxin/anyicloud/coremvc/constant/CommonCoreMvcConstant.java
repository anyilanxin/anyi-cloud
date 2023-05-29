

package com.anyilanxin.anyicloud.coremvc.constant;

/**
 * 全局公共常量
 *
 * @author zxh
 * @date 2020-07-23 19:57
 * @since 1.0.0
 */
public interface CommonCoreMvcConstant {
    String CONST_PREFIX = "coremvc_";

    /**
     * 测试缓存key
     */
    String TEST_DEMO_CACHE = "test-webmvc:demo";

    /**
     * 雪花终端id
     */
    long WORKER_ID = 12L;

    /**
     * 雪花数据中心id
     */
    long DATACENTER_ID = 12L;

    /**
     * 线程池前缀
     */
    String TASK_EXECUTOR_PREFIX = "taskExecutor-";
}
