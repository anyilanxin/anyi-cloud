// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.constant;

/**
 * 全局公共常量
 *
 * @author zxiaozhou
 * @date 2020-07-23 19:57
 * @since JDK11
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
