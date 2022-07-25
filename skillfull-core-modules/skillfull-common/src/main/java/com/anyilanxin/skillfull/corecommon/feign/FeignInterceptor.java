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


import com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant;
import com.anyilanxin.skillfull.corecommon.feign.strategy.header.ContextHeaderStrategy;
import com.anyilanxin.skillfull.corecommon.feign.strategy.safety.ContextSafetyStrategy;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Feign拦截器
 *
 * @author zxiaozhou
 * @date 2019-04-24 14:19
 * @since JDK11
 */
@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {
    private ContextHeaderStrategy contextHeaderStrategy;
    private ContextSafetyStrategy contextSafetyStrategy;

    /**
     * 本处不能使用构造器注入,因为会造成feign拦截器失效
     *
     * @param contextHeaderStrategy ${@link ContextHeaderStrategy} 请求头设置策略上下文
     * @author zxiaozhou
     * @date 2019-05-15 23:00
     */
    @Autowired
    private void setContextHeaderStrategy(final ContextHeaderStrategy contextHeaderStrategy) {
        this.contextHeaderStrategy = contextHeaderStrategy;
    }


    /**
     * 本处不能使用构造器注入,因为会造成feign拦截器失效
     *
     * @param contextSafetyStrategy ${@link ContextSafetyStrategy} 数据安全处理策略上下文
     * @author zxiaozhou
     * @date 2019-06-14 13:39
     */
    @Autowired
    private void setContextSafetyStrategy(final ContextSafetyStrategy contextSafetyStrategy) {
        this.contextSafetyStrategy = contextSafetyStrategy;
    }


    /**
     * token设置
     *
     * @param template ${@link RequestTemplate}
     * @author zxiaozhou
     * @date 2019-05-15 17:52
     */
    @Override
    public void apply(RequestTemplate template) {
        // 获取基本参数
        Map<String, String> strategies = getParams(template);
        contextHeaderStrategy.setHeader(SysBaseConstant.FEIGN_DEFAULT, template);
        // 处理请求头设置
        String headerStrategy = strategies.get("headerStrategy");
        if (StringUtils.isNotBlank(headerStrategy)) {
            contextHeaderStrategy.setHeader(headerStrategy, template);
        }
        // 处理数据安全
        String safetyStrategy = strategies.get("safetyStrategy");
        if (StringUtils.isNotBlank(safetyStrategy)) {
            contextSafetyStrategy.handleSafety(safetyStrategy, template);
        }
    }


    /**
     * 获取策略基本信息
     *
     * @param template ${@link RequestTemplate}
     * @return Map ${@link Map}
     * @author zxiaozhou
     * @date 2019-06-14 12:11
     */
    private Map<String, String> getParams(RequestTemplate template) {
        Map<String, Collection<String>> queries = new LinkedHashMap<>(template.queries());
        Map<String, String> strategies = new HashMap<>(2);
        if (!queries.isEmpty()) {
            // 获取请求头设置策略
            Collection<String> headerStrategy = queries.remove("headerStrategy");
            if (!CollectionUtils.isEmpty(headerStrategy)) {
                String strategy = new ArrayList<>(headerStrategy).get(0);
                if (StringUtils.isNotBlank(strategy)) {
                    log.debug("----------FeignInterceptor---------->getParams:{}", "执行feign拦截器,当前需要设置请求头,调用策略" + strategy);
                    strategies.put("headerStrategy", strategy);
                }
            }
            // 获取数据安全设置策略
            Collection<String> safetyStrategy = queries.remove("safetyStrategy");
            if (!CollectionUtils.isEmpty(safetyStrategy)) {
                String strategy = new ArrayList<>(safetyStrategy).get(0);
                if (StringUtils.isNotBlank(strategy)) {
                    log.debug("----------FeignInterceptor---------->getParams:{}", "执行feign拦截器,当前数据需要安全处理,调用策略" + strategy);
                    strategies.put("safetyStrategy", strategy);
                }
            }
        }
        // 重置请求地址参数
        template.queries(null);
        template.queries(queries);
        return strategies;
    }
}
