package com.anyilanxin.skillfull.logging.core.config;


import com.anyilanxin.skillfull.coremvc.base.service.ICoreWebmvcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动处理
 *
 * @author zxiaozhou
 * @date 2019-04-16 10:38
 * @since JDK11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingStartConfig implements ApplicationRunner {
    private final ICoreWebmvcService coreCommonService;


    @Override
    public void run(ApplicationArguments args) {
        // 加载常量字典
        coreCommonService.loadConstantDict(false);
    }
}
