

package com.anyilanxin.anyicloud.storagerpc;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * process AutoConfiguration
 *
 * @author zxh
 * @date 2022-03-29 10:44
 * @since 1.0.0
 */
@AutoConfiguration
@ComponentScan
@EnableFeignClients(basePackages = "com.anyilanxin.skillfull.storagerpc.feign")
public class StorageRpcAutoConfiguration {
}
