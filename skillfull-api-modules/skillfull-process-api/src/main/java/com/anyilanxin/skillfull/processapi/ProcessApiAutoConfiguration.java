// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processapi;

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
@EnableFeignClients(basePackages = "com.anyilanxin.skillfull.processapi.feign")
public class ProcessApiAutoConfiguration {
}
