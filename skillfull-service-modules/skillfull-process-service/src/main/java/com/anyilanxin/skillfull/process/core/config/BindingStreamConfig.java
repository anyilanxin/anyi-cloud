// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.core.config;

import com.anyilanxin.skillfull.corecommon.constant.BindingStreamConstant;
import com.anyilanxin.skillfull.process.modules.business.service.IProcessBusinessService;
import com.anyilanxin.skillfull.processapi.model.MsgSubmitProcessModel;
import com.anyilanxin.skillfull.processapi.model.ProcessCallbackResultModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
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
    private final IProcessBusinessService processBusinessService;
    private final StreamBridge streamBridge;


    /**
     * 发起流程
     *
     * @return Consumer<MsgSubmitProcessModel> ${@link Consumer<MsgSubmitProcessModel>}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.SUBMIT_PROCESS)
    public Consumer<MsgSubmitProcessModel> submitProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------接收到消息启动流程事件------>submitProcess:{}", payload);
            ProcessCallbackResultModel resultModel = processBusinessService.submitProcess(payload);
            log.debug("------------BindingStreamConfig------消息启动流程结果------>submitProcess:{}", resultModel);
            streamBridge.send(BindingStreamConstant.SUBMIT_PROCESS_RESULT + BindingStreamConstant.OUT_SUFFIX, resultModel);
        };
    }
}
