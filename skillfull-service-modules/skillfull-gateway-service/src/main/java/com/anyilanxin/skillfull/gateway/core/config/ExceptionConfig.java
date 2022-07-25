// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.core.config;

import com.anyilanxin.skillfull.gateway.core.handler.WebExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * 异常处理器配置
 *
 * @author zxiaozhou
 * @date 2020-09-11 18:10
 * @since JDK11
 */
@Configuration
@EnableConfigurationProperties({ServerProperties.class, WebProperties.class})
public class ExceptionConfig {


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes,
                                                             ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer,
                                                             ServerProperties serverProperties,
                                                             ApplicationContext applicationContext,
                                                             WebProperties webProperties) {
        WebExceptionHandler exceptionHandler = new WebExceptionHandler(
                errorAttributes,
                webProperties,
                serverProperties.getError(),
                applicationContext);
        exceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        exceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }

}