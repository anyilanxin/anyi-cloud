

package com.anyilanxin.anyicloud.process.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * camunda数据有配置
 *
 * @author zxh
 * @date 2022-04-04 20:27
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class CamundaDataSourceConfig {
    // @Bean
    // @Primary
    // @ConfigurationProperties(prefix = "spring.datasource.dynamic.master")
    // public DataSource primaryDataSource() {
    // return DataSourceBuilder.create().build();
    // }

    //
    // @Bean(name = "camundaBpmDataSource")
    // @ConfigurationProperties(prefix = "datasource.secondary")
    // public DataSource secondaryDataSource() {
    // return DataSourceBuilder.create().build();
    // }
    //
    //
    // @Bean(name = "camundaBpmTransactionManager")
    // public PlatformTransactionManager secondTransactionManager(DataSource
    // dataSource) {
    // return new DataSourceTransactionManager(dataSource);
    // }
}
