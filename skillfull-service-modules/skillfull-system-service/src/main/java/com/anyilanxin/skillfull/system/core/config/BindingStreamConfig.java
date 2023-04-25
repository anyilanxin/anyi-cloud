package com.anyilanxin.skillfull.system.core.config;

import com.anyilanxin.skillfull.corecommon.constant.BindingStreamConstant;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * stream配置
 *
 * @author zxiaozhou
 * @date 2021-05-29 16:59
 * @since JDK1.8
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
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.AUTH_URL_PROCESS)
    public Consumer<String> authUrlProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>authUrlProcess:\n{}", payload);
//            routeService.refreshRoute(true);
        };
    }
}
