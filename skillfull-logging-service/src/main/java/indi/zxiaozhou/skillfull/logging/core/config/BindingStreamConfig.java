// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.logging.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.model.stream.AuthLogModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.OperateLogModel;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.logging.modules.manage.service.IAuthDataService;
import indi.zxiaozhou.skillfull.logging.modules.manage.service.IOperateService;
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
    private final IAuthDataService authDataService;
    private final IOperateService operateService;

    /**
     * 操作日志
     *
     * @return Consumer<OperateLogModel> ${@link Consumer <OperateLogModel>}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.OPERATE_LOG_PROCESS)
    public Consumer<OperateLogModel> operateLogProcess() {
        return operateService::save;
    }


    /**
     * 授权日志
     *
     * @return Consumer<AuthLogModel> ${@link Consumer <AuthLogModel>}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.AUTH_LOG_PROCESS)
    public Consumer<AuthLogModel> authLogProcess() {
        return authDataService::save;
    }
}
