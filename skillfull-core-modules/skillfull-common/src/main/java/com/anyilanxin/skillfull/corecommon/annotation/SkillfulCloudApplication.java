package com.anyilanxin.skillfull.corecommon.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.annotation.AliasFor;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

import static com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;

/**
 * 自定义启动注解
 *
 * @author zxiaozhou
 * @date 2021-01-12 17:16
 * @since JDK11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@EnableDiscoveryClient
@EnableRetry
@EnableAsync
@EnableCaching
public @interface SkillfulCloudApplication {
    @AliasFor(annotation = SpringBootApplication.class, attribute = "scanBasePackages")
    String[] scanBasePackages() default BOOT_BASE_SCAN_PACKAGE;

    @AliasFor(annotation = SpringBootApplication.class, attribute = "scanBasePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

    @AliasFor(annotation = SpringBootApplication.class)
    boolean proxyBeanMethods() default true;
}
