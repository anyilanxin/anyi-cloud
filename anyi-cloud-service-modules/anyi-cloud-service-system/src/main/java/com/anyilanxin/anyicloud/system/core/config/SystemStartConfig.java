

package com.anyilanxin.anyicloud.system.core.config;

import com.anyilanxin.anyicloud.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动处理
 *
 * @author zxh
 * @date 2019-04-16 10:38
 * @since 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SystemStartConfig implements ApplicationRunner {
    private final ICoreWebmvcService coreCommonService;
    private final IManageSyncService syncService;

    @Override
    public void run(ApplicationArguments args) {
        // 加载常量字典
        coreCommonService.loadConstantDict(false);
        // 路由信息写入redis并通知网关
        log.debug("------------StartConfig------------>loadRoute:{}", "开始加载路由权限信息");
        syncService.reloadRoute(false);
    }
}
