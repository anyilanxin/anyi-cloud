

package com.anyilanxin.anyicloud.corecommon.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;

import java.util.Locale;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * key配置
 *
 * @author zxh
 * @date 2020-08-28 10:23
 * @since 1.0.0
 */
@AutoConfiguration
public class CoreCommonConfig {
    /**
     * 雪花生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public Snowflake snowflake() {
        return IdUtil.getSnowflake(CommonCoreConstant.WORKER_ID, CommonCoreConstant.DATACENTER_ID);
    }


    /**
     * i18n支持
     */
    @Bean
    @ConditionalOnMissingBean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:i18n/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}
