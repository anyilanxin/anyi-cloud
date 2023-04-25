package com.anyilanxin.skillfull.corecommon.feign.strategy.safety;


import feign.RequestTemplate;

/**
 * token设置策略接口
 *
 * @author zxiaozhou
 * @date 2019-02-03 21:32
 * @since JDK11
 */
public interface ISafetyStrategy {
    /**
     * setToken
     *
     * @param template {@link RequestTemplate} RequestTemplate
     * @author zxiaozhou
     * @date 2019-02-19 16:28
     */
    void handleSafety(RequestTemplate template);

}
