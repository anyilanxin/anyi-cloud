package com.anyilanxin.skillfull.systemrpc;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * process AutoConfiguration
 *
 * @author zxiaozhou
 * @date 2022-03-29 10:44
 * @since JDK1.8
 */
@AutoConfiguration
@ComponentScan
@EnableFeignClients(basePackages = "com.anyilanxin.skillfull.systemrpc.feign")
public class SystemRpcAutoConfiguration {
}
