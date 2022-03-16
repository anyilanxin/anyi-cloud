// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatabase.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * @author zxiaozhou
 */
@Configuration
public class MybatisPlusCoreDatabaseConfig {

    /**
     * 分页插件，自动识别数据库类型
     *
     * @return MybatisPlusInterceptor ${@link MybatisPlusInterceptor}
     * @author zxiaozhou
     * @date 2019-04-03 15:21
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


    /**
     * sql日志打印(只用于开发和测试环境)
     *
     * @return MybatisConfiguration ${@link MybatisConfiguration}
     * @author zxiaozhou
     * @date 2019-04-03 17:58
     */
    @Bean
    @ConditionalOnMissingBean
    @Profile({"dev", "test"})
    public MybatisConfiguration mybatisConfiguration() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setLogImpl(StdOutImpl.class);
        return mybatisConfiguration;
    }
}
