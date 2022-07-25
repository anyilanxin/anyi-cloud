// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.anyilanxin.skillfull.corecommon.auth.GetLoginUserInfoDefault;
import com.anyilanxin.skillfull.corecommon.auth.IGetLoginUserInfo;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;


/**
 * key配置
 *
 * @author zxiaozhou
 * @date 2020-08-28 10:23
 * @since JDK11
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


    /**
     * 默认获取用户信息(防止报错)
     */
    @Bean
    @ConditionalOnMissingBean(IGetLoginUserInfo.class)
    public IGetLoginUserInfo getLoginUserInfo() {
        return new GetLoginUserInfoDefault();
    }
}
