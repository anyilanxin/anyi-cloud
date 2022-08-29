// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * camunda数据有配置
 *
 * @author zxiaozhou
 * @date 2022-04-04 20:27
 * @since JDK1.8
 */
@Configuration
@RequiredArgsConstructor
public class CamundaDataSourceConfig {
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic.master")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    //
//    @Bean(name = "camundaBpmDataSource")
//    @ConfigurationProperties(prefix = "datasource.secondary")
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//
//    @Bean(name = "camundaBpmTransactionManager")
//    public PlatformTransactionManager secondTransactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
