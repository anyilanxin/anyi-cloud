// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc.config;

import com.anyilanxin.skillfull.corecommon.constant.BindingStreamConstant;
import com.anyilanxin.skillfull.oauth2common.config.AuthConfigAttributeModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

/**
 * url权限stream配置
 *
 * @author zxiaozhou
 * @date 2021-05-29 16:59
 * @since JDK1.8
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class BindingUrlAuthStreamConfig {
    private final AuthConfigAttributeModel authConfigAttributeModel;

    /**
     * 处理刷新权限，此处只需要清空内存数据即可，当请求到服务时会由RedisRequestConfigMappingService重新加载
     *
     * @return Consumer<String> ${@link Consumer<String>}
     * @author zxiaozhou
     * @date 2021-05-29 17:00
     */
    @Bean(value = BindingStreamConstant.AUTH_URL_PROCESS)
    public Consumer<String> authUrlProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>authUrlProcess:\n{}", payload);
            authConfigAttributeModel.setAuthConfigAttribute(null);
        };
    }
}
