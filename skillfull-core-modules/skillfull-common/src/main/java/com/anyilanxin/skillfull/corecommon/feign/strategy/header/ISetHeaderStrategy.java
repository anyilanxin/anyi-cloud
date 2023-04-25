package com.anyilanxin.skillfull.corecommon.feign.strategy.header;


import feign.RequestTemplate;

/**
 * token设置策略接口
 *
 * @author zxiaozhou
 * @date 2019-02-03 21:32
 * @since JDK11
 */
public interface ISetHeaderStrategy {
    /**
     * setToken
     *
     * @param template {@link RequestTemplate} RequestTemplate
     * @author zxiaozhou
     * @date 2019-02-19 16:28
     */
    void setHeader(RequestTemplate template);

}
