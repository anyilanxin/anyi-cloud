

package com.anyilanxin.anyicloud.message.core.config;

import com.anyilanxin.anyicloud.message.core.handler.MyMetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;

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
     * 存储websocket session,键为sessionId
     *
     * @return WebSocketCacheModel
     * @author zxh
     * @date 2022-03-29 16:10
     */
    @Bean
    public ConcurrentHashMap<String, Session> socketSessionsCache() {
        return new ConcurrentHashMap<>();
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
}
