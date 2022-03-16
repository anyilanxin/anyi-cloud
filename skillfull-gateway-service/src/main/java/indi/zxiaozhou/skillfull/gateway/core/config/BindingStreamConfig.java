// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.model.stream.ConfigTokenModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.SystemRouterListModel;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.gateway.modules.manage.service.IDynamicRouteService;
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
    private final IDynamicRouteService routeService;

    /**
     * 处理路由消息
     *
     * @return Consumer<String> ${@link Consumer<String>}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.ROUTER_PROCESS)
    public Consumer<SystemRouterListModel> routerProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>routerProcess:\n{}", payload);
            routeService.loadRoute(payload);
        };
    }


    /**
     * 系统配置信息
     *
     * @return Consumer<BaseSystemModel> ${@link Consumer< ConfigTokenModel >}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.BASE_CONFIG_PROCESS)
    public Consumer<ConfigTokenModel> baseConfigProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>baseConfigProcess:\n{}", payload);
        };
    }
}
