// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatapermflux.config;

import indi.zxiaozhou.skillfull.coredatapermflux.interceptor.MybatisDataPermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis拦截器配置
 *
 * @author zhou
 * @date 2019-11-18 16:34
 * @since JDK1.8
 */
@Configuration
public class MybatisInterceptorConfig {

    static final String PARAM_INTERCEPTOR_BEAN_NAME = "lazyParamInterceptor";

    @Bean(name = PARAM_INTERCEPTOR_BEAN_NAME)
    public MybatisDataPermissionInterceptor paramInterceptor() {
        return new MybatisDataPermissionInterceptor();
    }
}
