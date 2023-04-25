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
