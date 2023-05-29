

package com.anyilanxin.anyicloud.logging.core.config;

import com.anyilanxin.anyicloud.database.injector.MysqlBatchInjector;
import com.anyilanxin.anyicloud.logging.core.handler.MyMetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zxh
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 插件配置，分页、加解密、数据权限等
     *
     * @author zxh
     * @date 2019-04-03 15:21
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor plusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


    /**
     * sql公共字段自定义注入
     *
     * @return MyMetaObjectHandler ${@link MetaObjectHandler}
     * @author zxh
     * @date 2019-04-03 18:10
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }


    /**
     * mysql真正批量插入注入器
     *
     * @return {@link MysqlBatchInjector }
     * @author zxh
     * @date 2022-10-07 19:06:23
     */
    @Bean
    public MysqlBatchInjector mysqlBatchInjector() {
        return new MysqlBatchInjector();
    }
}
