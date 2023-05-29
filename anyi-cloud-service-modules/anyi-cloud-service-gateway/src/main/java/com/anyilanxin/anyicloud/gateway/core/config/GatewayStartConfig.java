

package com.anyilanxin.anyicloud.gateway.core.config;

import com.anyilanxin.anyicloud.corewebflux.base.service.ICoreWebfluxService;
import com.anyilanxin.anyicloud.corewebflux.config.properfy.SpringDocCoreWebFluxProperty;
import com.anyilanxin.anyicloud.gateway.modules.manage.service.IDynamicRouteService;
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
public class GatewayStartConfig implements ApplicationRunner {
    private final IDynamicRouteService routeService;
    private final ICoreWebfluxService coreCommonService;
    private final SpringDocCoreWebFluxProperty webFluxProperty;

    @Override
    public void run(ApplicationArguments args) {
        // 加载路由信息
        routeService.loadRoute();
        // 加载常量字典
        coreCommonService.loadConstantDict(false);
    }
}
