

package com.anyilanxin.anyicloud.logging.core.config;

import com.anyilanxin.anyicloud.logging.modules.receive.service.IReceiveService;
import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import com.anyilanxin.anyicloud.loggingcommon.model.OperateLogModel;
import com.anyilanxin.anyicloud.stream.constant.BindingStreamConstant;

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
    private final IReceiveService receiveService;

    /**
     * 操作日志
     *
     * @return Consumer<OperateLogModel> ${@link Consumer <OperateLogModel>}
     * @author zxh
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.OPERATE_LOG_PROCESS)
    public Consumer<OperateLogModel> operateLogProcess() {
        return receiveService::saveOperate;
    }


    /**
     * 授权日志
     *
     * @return Consumer<AuthLogModel> ${@link Consumer <AuthLogModel>}
     * @author zxh
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.AUTH_LOG_PROCESS)
    public Consumer<AuthLogModel> authLogProcess() {
        return receiveService::saveAuth;
    }
}
