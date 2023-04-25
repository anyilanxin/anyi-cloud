package com.anyilanxin.skillfull.auth.core.config;

import com.anyilanxin.skillfull.auth.core.handler.MyMetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zxiaozhou
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 插件配置，分页、加解密、数据权限等
     *
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2019-04-03 18:10
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }


}
