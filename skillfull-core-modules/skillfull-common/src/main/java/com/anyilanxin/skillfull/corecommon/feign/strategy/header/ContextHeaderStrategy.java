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

import com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * token设置策略上下文
 *
 * @author zxiaozhou
 * @date 2019-02-03 21:33
 * @since JDK11
 */
@Component
@Slf4j
public class ContextHeaderStrategy {
    private final static Map<String, ISetHeaderStrategy> HEADER_STRATEGY = new ConcurrentHashMap<>();


    @Autowired
    public ContextHeaderStrategy(Map<String, ISetHeaderStrategy> headerStrategy) {
        HEADER_STRATEGY.putAll(headerStrategy);
    }

    public void setHeader(String strategy, RequestTemplate template) {
        // 先调用默认的
        ISetHeaderStrategy iSetHeaderStrategy = HEADER_STRATEGY.get(SysBaseConstant.FEIGN_DEFAULT);
        if (Objects.nonNull(iSetHeaderStrategy)) {
            try {
                iSetHeaderStrategy.setHeader(template);
            } catch (Exception e) {
                log.error("------------ContextHeaderStrategy-----feign设置请求头异常------->setHeader--->异常消息:{}", e.getMessage());
            }
        }
        // 在调用其他的
        ISetHeaderStrategy setTokenStrategy = HEADER_STRATEGY.get(strategy);
        if (setTokenStrategy != null) {
            try {
                setTokenStrategy.setHeader(template);
            } catch (Exception e) {
                log.error("------------ContextHeaderStrategy-----feign设置请求头异常------->setHeader--->异常消息:{}", e.getMessage());
            }
        } else {
            log.error("----------ContextStrategy---------->doStrategy:{}", "Api调用未找到设置请求头的方法");
        }
    }
}
