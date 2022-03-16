// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.lang.annotation.*;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;

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
@SpringBootApplication(scanBasePackages = BOOT_BASE_SCAN_PACKAGE)
@EnableDiscoveryClient
public @interface SkillfulCloudApplication {
}
