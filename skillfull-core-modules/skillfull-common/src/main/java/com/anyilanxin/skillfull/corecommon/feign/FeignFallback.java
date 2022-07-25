// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.feign;


import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * feign fall back
 *
 * @author zxiaozhou
 * @date 2019-04-09 12:05
 * @since JDK11
 */
public class FeignFallback<T> implements FallbackFactory<T> {

    @Override
    public T create(Throwable cause) {
        return null;
    }
}
