// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.model.stream.ProcessSubmitResultModel;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
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

    /**
     * 发起流程后结果
     *
     * @return Consumer<ProcessSubmitResultModel> ${@link Consumer< ProcessSubmitResultModel >}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.SUBMIT_PROCESS_RESULT)
    public Consumer<ProcessSubmitResultModel> submitProcessResult() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>baseConfigProcess:\n{}", payload);
        };
    }
}
