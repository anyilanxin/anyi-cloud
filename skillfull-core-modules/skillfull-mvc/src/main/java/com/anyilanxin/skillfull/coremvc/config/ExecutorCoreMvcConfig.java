// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.config;

import com.anyilanxin.skillfull.coremvc.constant.CommonCoreMvcConstant;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程配置
 *
 * @author zxiaozhou
 * @date 2020-08-28 10:23
 * @since JDK11
 */
@AutoConfiguration
public class ExecutorCoreMvcConfig {
    /**
     * 线程池配置
     *
     * @return Executor ${@link Executor}
     * @author zxiaozhou
     * @date 2020-08-28 14:38
     */
    @Bean
    @ConditionalOnMissingBean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（默认线程数）
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 缓冲队列数
        executor.setQueueCapacity(200);
        // 允许线程空闲时间（单位：默认为秒）
        executor.setKeepAliveSeconds(60);
        // 线程池名前缀
        executor.setThreadNamePrefix(CommonCoreMvcConstant.TASK_EXECUTOR_PREFIX);
        // 增加 TaskDecorator 属性的配置线程间同步数据
//        executor.setTaskDecorator(new ContextDecorator());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


    /**
     * 线程池上下文传递
     *
     * @author zxiaozhou
     * @date 2020-08-28 14:37
     * @since JDK11
     */
    static class ContextDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(context);
                    runnable.run();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}
