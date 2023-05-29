

package com.anyilanxin.anyicloud.corecommon.feign;

import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * feign fall back
 *
 * @author zxh
 * @date 2019-04-09 12:05
 * @since 1.0.0
 */
public class FeignFallback<T> implements FallbackFactory<T> {

    @Override
    public T create(Throwable cause) {
        return null;
    }
}
