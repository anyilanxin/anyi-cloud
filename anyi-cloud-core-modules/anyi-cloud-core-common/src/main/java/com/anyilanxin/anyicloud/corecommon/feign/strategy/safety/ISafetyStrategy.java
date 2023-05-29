

package com.anyilanxin.anyicloud.corecommon.feign.strategy.safety;

import feign.RequestTemplate;

/**
 * token设置策略接口
 *
 * @author zxh
 * @date 2019-02-03 21:32
 * @since 1.0.0
 */
public interface ISafetyStrategy {
    /**
     * setToken
     *
     * @param template {@link RequestTemplate} RequestTemplate
     * @author zxh
     * @date 2019-02-19 16:28
     */
    void handleSafety(RequestTemplate template);
}
