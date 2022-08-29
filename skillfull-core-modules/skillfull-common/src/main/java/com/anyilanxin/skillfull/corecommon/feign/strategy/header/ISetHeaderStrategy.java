// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
