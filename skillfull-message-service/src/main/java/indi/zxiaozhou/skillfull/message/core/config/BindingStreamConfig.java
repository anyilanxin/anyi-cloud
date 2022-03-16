// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.ProcessMsgModel;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.message.modules.websocket.service.IWebSocketMsgService;
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
    private final IWebSocketMsgService socketMsgService;

    /**
     * 处理socket广播
     *
     * @return Consumer<SocketResult>  ${@link Consumer< SocketResult > }
     * @author zxiaozhou
     * @date 2021-05-29 17:01
     */
    @Bean(value = BindingStreamConstant.SOCKET_PROCESS)
    public Consumer<SocketResult> socketProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>socketProcess:\n{}", payload);
            socketMsgService.socketProcess(payload);
        };
    }


    /**
     * 处理流程消息
     *
     * @return Consumer<ProcessMsgModel>  ${@link Consumer<ProcessMsgModel> }
     * @author zxiaozhou
     * @date 2021-05-29 17:01
     */
    @Bean(value = BindingStreamConstant.PROCESS_MSG_PROCESS)
    public Consumer<ProcessMsgModel> processMsgProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到流程引擎的消息------>socketProcess:\n{}", payload);
        };
    }
}
