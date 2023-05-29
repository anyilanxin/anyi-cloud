

package com.anyilanxin.anyicloud.system.core.config;

import com.anyilanxin.anyicloud.corecommon.constant.BindingStreamConstant;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageRouteService;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * stream配置
 *
 * @author zxh
 * @date 2021-05-29 16:59
 * @since 1.0.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BindingStreamConfig {
    private final IManageRouteService routeService;

    /**
     * 处理刷新权限
     *
     * @return Consumer<String> ${@link Consumer<String>}
     * @author zxh
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.AUTH_URL_PROCESS)
    public Consumer<String> authUrlProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>authUrlProcess:\n{}", payload);
            // routeService.refreshRoute(true);
        };
    }
}
